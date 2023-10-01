package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Newspaper extends Literature implements Periodic, Printable
{
    @Required
    private Date date;
    private static final SimpleDateFormat cardFormat =
            new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat sqldateFormat =
        new SimpleDateFormat("yyyy-MM-dd");
    public void setDate(Date date)
    {
        this.date = date;
    }
    public Date getDate()
    {
        return this.date;
    }

    public Newspaper(Date date, String title)
    {
        this.setDate(date);
        super.setTitle(title);
    }
    public Newspaper(String date, String title) throws ParseException
    {
        this( sqldateFormat.parse(date), title);
    }

    @Override
    public String getCard()
    {
        return String.format(
                "Newspaper : '%s' from %s",
                super.getTitle(),
                cardFormat.format(this.getDate())
        );
    }
    @Override
    public String getPeriod()
    {
        return "N getPeriod";
    }

    private static List<String> requiredFieldsNames;
    public static List<String> getRequiredFieldsNames()
    {
        if(requiredFieldsNames == null)
        {
            Field[] fields = Newspaper.class.getDeclaredFields();
            Field[] fields2 = Newspaper.class.getSuperclass().getDeclaredFields();
            requiredFieldsNames = Stream.concat(
                            Arrays.stream(fields),
                            Arrays.stream(fields2))
                    .filter( field -> field.isAnnotationPresent(Required.class) )
                    .map( Field::getName )
                    .collect(Collectors.toList());
        }
        return requiredFieldsNames;
    }
    public static void setRequiredFieldsNames(List<String> _requiredFieldsNames)
    {
        requiredFieldsNames = _requiredFieldsNames;
    }

    @FromJsonParser
    public static Newspaper fromJson(JsonObject jsonObject) throws ParseException
    {
        List<String> requiredFields = getRequiredFieldsNames();
        for(String field : requiredFields)
        {
            if(! jsonObject.has(field))
            {
                throw new ParseException("Missing require field : " + field, 0);
            }
        }
        return new Newspaper(
                jsonObject.get(requiredFields.get(0)).getAsString(),
                jsonObject.get(requiredFields.get(1)).getAsString()
        );
    }
    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject)
    {
        for(String field : getRequiredFieldsNames())
        {
            if(! jsonObject.has(field))
            {
                return false;
            }
        }
        return true;
    }
}
