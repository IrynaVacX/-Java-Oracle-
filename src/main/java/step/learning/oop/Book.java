package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Book extends Literature implements Copyable, Printable, Multiple
{
    @Required
    private String author;
    @Required
    private int count;
    public void setAuthor(String author)
    {
        this.author = author;
    }
    public String getAuthor()
    {
        return this.author;
    }
    public void setCount(int count)
    {
        this.count = count;
    }
    @Override
    public int getCount()
    {
        return this.count;
    }

    private static List<String> requiredFieldsNames;
    public static List<String> getRequiredFieldsNames()
    {
        if(requiredFieldsNames == null)
        {
            Field[] fields = Book.class.getDeclaredFields();
            Field[] fields2 = Book.class.getSuperclass().getDeclaredFields();
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

    public Book(String author, String title)
    {
        this.setAuthor(author);
        super.setTitle(title);
    }
    public Book(String author, int count, String title)
    {
        this.setAuthor(author);
        this.setCount(count);
        super.setTitle(title);
    }
    @Override
    public String getCard()
    {
        return String.format(
                "Book : %s '%s'  (x%s)",
                this.getAuthor(),
                super.getTitle(),
                this.getCount()
        );
    }

    @FromJsonParser
    public static Book fromJson(JsonObject jsonObject) throws ParseException
    {
        List<String> requiredFields = getRequiredFieldsNames();
        for(String field : requiredFields)
        {
            if(! jsonObject.has(field))
            {
                throw new ParseException("Missing require field : " + field, 0);
            }
        }
        return new Book(
                jsonObject.get(requiredFields.get(0)).getAsString(),
                jsonObject.get(requiredFields.get(1)).getAsInt(),
                jsonObject.get(requiredFields.get(2)).getAsString()
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
