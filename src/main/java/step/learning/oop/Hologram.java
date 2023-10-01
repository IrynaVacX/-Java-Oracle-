package step.learning.oop;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Serializable
public class Hologram extends Literature
{
    @Required
    private String description;
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getDescription()
    {
        return description;
    }

    public Hologram(String description, String title)
    {
        super.setTitle(title);
        this.setDescription(description);
    }

    private static List<String> requiredFieldsNames;
    public static List<String> getRequiredFieldsNames()
    {
        if(requiredFieldsNames == null)
        {
            Field[] fields = Hologram.class.getDeclaredFields();
            Field[] fields2 = Hologram.class.getSuperclass().getDeclaredFields();
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
    public static Hologram fromJson(JsonObject jsonObject) throws ParseException
    {
        List<String> requiredFields = getRequiredFieldsNames();
        for (String field : requiredFields)
        {
            if(!jsonObject.has(field))
            {
                throw new ParseException("Missing required field: " + field, 0);
            }
        }
        return new Hologram(
                jsonObject.get(requiredFields.get(0)).getAsString(),
                jsonObject.get(requiredFields.get(1)).getAsString()
        );
    }
    @ParseChecker
    public static boolean isParseableFromJson(JsonObject jsonObject)
    {
        List<String> requiredFields = getRequiredFieldsNames();
        for (String field : requiredFields)
        {
            if(!jsonObject.has(field))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getCard()
    {
        return String.format("Hologram : %s [ %s ]",
                this.getTitle(),
                this.getDescription()
        );
    }

}
