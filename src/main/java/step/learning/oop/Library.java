package step.learning.oop;

import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Library implements Copyable, Periodic
{
    private final List<Literature> funds;
    public List<Literature> getFunds()
    {
        return funds;
    }
    private List<Literature> getSerializableFunds()
    {
        List<Literature> serializableFunds = new ArrayList<>();
        for(Literature literature : getFunds())
        {
            if(literature.getClass().isAnnotationPresent(Serializable.class))
            {
                serializableFunds.add(literature);
            }
        }
        return serializableFunds;
    }

    public void save() throws IOException
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
        FileWriter writer = new FileWriter("./src/main/resources/library.json");
        writer.write(gson.toJson(this.getSerializableFunds()));
        writer.close();
    }
    public void load()
    {
        try(InputStreamReader reader = new InputStreamReader(
                Objects.requireNonNull(
                        this.getClass().getClassLoader().getResourceAsStream("library.json"))
                )
        ) {
            this.funds.clear();
            for(JsonElement item : JsonParser.parseReader(reader).getAsJsonArray())
            {
                this.funds.add( this.fromJson(item.getAsJsonObject()) );
            }
        }
        catch (IOException | ParseException ex)
        {
            throw new RuntimeException(ex);
        }
        catch (NullPointerException ignored)
        {
            throw new RuntimeException("Resource not found");
        }
    }
    private List<Class<?>> getSerializableClasses()
    {
        List<Class<?>> literatures = new LinkedList<>();

        String className = Literature.class.getName();
        String packageName = className.substring(0, className.lastIndexOf('.'));
        String packagePath = packageName.replace(".","/");
        String absolutePath = Literature.class.getClassLoader()
                .getResource(packagePath).getPath();
        File[] files = new File(absolutePath).listFiles();
        if(files == null)
        {
            throw new RuntimeException("Class path inaccessible");
        }
        for(File file : files)
        {
            if(file.isFile())
            {
                String filename = file.getName();
                if(filename.endsWith(".class"))
                {
                    String fileClassName = filename.substring(0, filename.lastIndexOf('.'));
                    try
                    {
                        Class<?> fileClass = Class.forName(packageName + "." + fileClassName);
                        if(fileClass.isAnnotationPresent((Serializable.class)))
                        {
                            literatures.add(fileClass);
                        }
                    }
                    catch (ClassNotFoundException ignored)
                    {
                        continue;
                    }
                }
            }
            else if(file.isDirectory())
            {
                continue;
            }
        }
        return literatures;
    }
    private Literature fromJson(JsonObject jsonObject) throws ParseException
    {
        List<Class<?>> literatures = this.getSerializableClasses();
        try
        {
            for(Class<?> literature : literatures)
            {
                // Method isParseableFromJson = literature.getDeclaredMethod("isParseableFromJson", JsonObject.class);
                Method isParseableFromJson = null;
                for(Method method : literature.getDeclaredMethods())
                {
                    if(method.isAnnotationPresent(ParseChecker.class))
                    {
                        if(isParseableFromJson != null)
                        {
                            throw new ParseException("Multiple ParseChecker annotations", 0);
                        }
                        isParseableFromJson = method;
                    }
                }
                if(isParseableFromJson == null)
                {
                    continue;
                }

                isParseableFromJson.setAccessible(true);
                boolean res = (boolean) isParseableFromJson.invoke(null, jsonObject);
                if(res)
                {
                    Method fromJson = literature.getDeclaredMethod("fromJson", JsonObject.class);
                    fromJson.setAccessible(true);
                    return (Literature) fromJson.invoke(null, jsonObject);
                }
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException("Reflection error : " + ex.getMessage());
        }

//        if(Book.isParseableFromJson(jsonObject))
//        {
//            return Book.fromJson(jsonObject);
//        }
//        else if(Journal.isParseableFromJson(jsonObject))
//        {
//            return Journal.fromJson(jsonObject);
//        }
//        else if(Newspaper.isParseableFromJson(jsonObject))
//        {
//            return Newspaper.fromJson(jsonObject);
//        }
//        else if(Hologram.isParseableFromJson(jsonObject))
//        {
//            return Hologram.fromJson(jsonObject);
//        }

        throw new ParseException("Literature type unrecognized", 0);
    }

    public Library()
    {
        this.funds = new LinkedList<>();
    }

    public void add(Literature funds)
    {
        this.funds.add(funds);
    }
    public void printAllCards()
    {
        for (Literature literature : this.funds)
        {
            System.out.println(literature.getCard());
        }
    }

    public void printCopyable()
    {
        for(Literature literature : funds)
        {
            if (isCopyable(literature))
            {
                System.out.println(literature.getCard());
            }
        }
    }
    public void printNonCopyable()
    {
        for(Literature literature : funds)
        {
            if (!isCopyable(literature))
            {
                System.out.println(literature.getCard());
            }
        }
    }
    public boolean isCopyable(Literature literature)
    {
        return (literature instanceof Copyable);
    }

    @Override
    public String getPeriod()
    {
        return null;
    }
    public void printPeriodic()
    {
        for(Literature literature : funds)
        {
            if (isPeriodic(literature))
            {
                Periodic litPeriodic = (Periodic) literature;
                System.out.println(litPeriodic.getPeriod() + " " + literature.getCard());
            }
        }
    }
    public void printNonPeriodic()
    {
        for(Literature literature : funds)
        {
            if (!isPeriodic(literature))
            {
                System.out.println(literature.getCard());
            }
        }
    }
    public boolean isPeriodic(Literature literature)
    {
        return (literature instanceof Periodic);
    }

    public void printPeriodic2()
    {
        for(Literature literature : funds)
        {
            try
            {
                Method getPeriodMethod = literature.getCard()
                        .getClass().getDeclaredMethod("getPeriod");
                System.out.println(
                        getPeriodMethod.invoke(literature) + " " + literature.getCard()
                );
            }
            catch (Exception ignored) { }
        }
    }

////////////////////////////////////////////////////////////
    public List<Literature> getPrintable()
    {
        List<Literature> result = new LinkedList<>();
        for(Literature literature : funds)
        {
            if(isPrintable(literature))
            {
                result.add(literature);
            }
        }
        return result;
    }
    public List<Literature> getNonPrintable()
    {
        List<Literature> result = new LinkedList<>();
        for(Literature literature : funds)
        {
            if(!isPrintable(literature))
            {
                result.add(literature);
            }
        }
        return result;
    }
    public boolean isPrintable(Literature literature)
    {
        return literature instanceof Printable;
    }

    public List<Literature> getMultiple()
    {
        List<Literature> result = new LinkedList<>();
        for(Literature literature : funds)
        {
            if(isMultiple(literature))
            {
                result.add(literature);
            }
        }
        return result;
    }
    public List<Literature> getNonMultiple()
    {
        List<Literature> result = new LinkedList<>();
        for(Literature literature : funds)
        {
            if(!isMultiple(literature))
            {
                result.add(literature);
            }
        }
        return result;
    }
    public boolean isMultiple(Literature literature)
    {
        return literature instanceof Multiple;
    }

}
