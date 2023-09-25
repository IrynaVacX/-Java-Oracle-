package step.learning.oop;

public class OopDemo
{
    public  void run()
    {
        Book book1 = new Book("D. Knuth", "Art of programming");
        Book book2 = new Book("A. Eden", "Main principles of OOP");
        // System.out.println(book1.getCard());

        Library library = new Library() ;
        library.add( book1 ) ;
        library.add( book2 ) ;
        library.printAllCards() ;
    }
}
