package step.learning.oop;

import com.google.gson.JsonObject;

import java.text.ParseException;

public abstract class Literature
{
    @Required
    private String title;
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public abstract String getCard();

    private Literature fromJson(JsonObject jsonObject) throws ParseException
    {
        if(Book.isParseableFromJson(jsonObject))
        {
            return Book.fromJson(jsonObject);
        }
        else if(Journal.isParseableFromJson(jsonObject))
        {
            return Journal.fromJson(jsonObject);
        }
        else if(Newspaper.isParseableFromJson(jsonObject))
        {
            return Newspaper.fromJson(jsonObject);
        }
        else if(Hologram.isParseableFromJson(jsonObject))
        {
            return Hologram.fromJson(jsonObject);
        }
        throw new ParseException("Literature type unrecognised", 0);
    }

}