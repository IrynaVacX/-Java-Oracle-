package step.learning.oop;

import java.util.LinkedList;
import java.util.List;

public class Library
{
    private final List<Book> books;

    public Library()
    {
        this.books = new LinkedList<>();
    }

    public void add(Book book)
    {
        this.books.add(book);
    }

    public void printAllCards()
    {
        for (Book book : this.books)
        {
            System.out.println(book.getCard());
        }
    }
}
