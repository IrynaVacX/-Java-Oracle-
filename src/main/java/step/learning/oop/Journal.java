package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Journal extends Literature implements Copyable, Periodic, Printable, Multiple
{
    @Required
    private int number;
    @Required
    private int count;
    public void setNumber(int number)
    {
        this.number = number;
    }
    public int getNumber()
    {
        return this.number;
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

    public Journal(String title, int number)
    {
        super.setTitle(title);
        this.setNumber(number);
    }
    public Journal(int number, int count, String title)
    {
        this.setNumber(number);
        this.setCount(count);
        super.setTitle(title);
    }
    @Override
    public String getCard()
    {
        return String.format(
                "Journal : №%s '%s'  (x%s)",
                this.getNumber(),
                super.getTitle(),
                this.getCount()
        );
    }
    @Override
    public String getPeriod()
    {
        return "J getPeriod";
    }

    private static List<String> requiredFieldsNames;
    public static List<String> getRequiredFieldsNames()
    {
        if(requiredFieldsNames == null)
        {
            Field[] fields = Journal.class.getDeclaredFields();
            Field[] fields2 = Journal.class.getSuperclass().getDeclaredFields();
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
    public static Journal fromJson(JsonObject jsonObject) throws ParseException
    {
        List<String> requiredFields = getRequiredFieldsNames();
        for(String field : requiredFields)
        {
            if(! jsonObject.has(field))
            {
                throw new ParseException("Missing require field : " + field, 0);
            }
        }
        return new Journal(
                jsonObject.get(requiredFields.get(0)).getAsInt(),
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
