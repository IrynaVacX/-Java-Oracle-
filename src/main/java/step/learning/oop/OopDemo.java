package step.learning.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Objects;

public class OopDemo
{
    public void HW_04_run()
    {
        Library library = new Library() ;
        try
        {
            library.add(new Book("D. Knuth", 2, "Art of programming"));
            library.add(new Book("Richter", 3, ".NET"));
            library.add(new Newspaper("2023-09-25", "Newspaper Title"));
            library.add(new Journal(2, 13, "Journal Title"));
            library.add(new Newspaper("2023-09-25", "Washington Post"));
            library.add(new Journal(1, 32, "Amogus"));

            library.add(new Hologram("Hologram-1", "hologram description 1"));
            library.add(new Hologram("Hologram-2", "hologram description 2"));

            library.save();
        }
        catch (ParseException e)
        {
            System.err.println("Literature creation error : " + e.getMessage());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println("===================PRINTABLE===================");
        library.getPrintable().forEach(e -> System.out.println(e.getCard()));
        System.out.println("=================NON-PRINTABLE=================");
        library.getNonPrintable().forEach(e -> System.out.println(e.getCard()));
        System.out.println("===================MULTIPLE===================");
        library.getMultiple().forEach(e -> System.out.println(e.getCard()));
        System.out.println("=================NON-MULTIPLE=================");
        library.getNonMultiple().forEach(e -> System.out.println(e.getCard()));
    }
    public void HW_05_run()
    {
        Library library = new Library();
        try
        {
            library.load();
        }
        catch (RuntimeException ex)
        {
            System.err.println(ex.getMessage());
        }
        library.printAllCards();
    }
    public void run4()
    {
        Library library = new Library();
        try
        {
            library.load();
        }
        catch (RuntimeException ex)
        {
            System.err.println(ex.getMessage());
        }
        library.printAllCards();
    }
    public void run3() throws ParseException
    {
        // JSON - засобами GSON
        Gson gson = new Gson();
        String str = "{\"author\" : \"D. Knuth\", \"title\" : \"Art of programming\" }";

        JsonObject literatureObject = JsonParser.parseString(str).getAsJsonObject();
        Literature literature;

        if(literatureObject.has("author"))
        {
            literature = new Book(
                    literatureObject.get("title").getAsString(),
                    literatureObject.get("author").getAsString()
            );
        }
        else if(literatureObject.has("number"))
        {
            literature = new Journal(
                    literatureObject.get("title").getAsString(),
                    literatureObject.get("number").getAsInt()
            );
        }
        else if(literatureObject.has("date"))
        {
            literature = new Newspaper(
                    literatureObject.get("title").getAsString(),
                    literatureObject.get("date").getAsString()
            );
        }
        else if(literatureObject.has("description"))
        {
            literature = new Hologram(
                    literatureObject.get("title").getAsString(),
                    literatureObject.get("description").getAsString()
            );
        }
        //System.out.println(literature.getCard());
    }
    public  void run2()
    {
        // JSON - засобами GSON
        Gson gson = new Gson();
        String str = "{\"author\" : \"D. Knuth\", \"title\" : \"Art of programming\" }";
        Book book = gson.fromJson(str, Book.class);
        System.out.println(book.getCard());

        System.out.println(gson.toJson(book));

        book.setAuthor(null);

        System.out.println(gson.toJson(book));

        Gson gson2 = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        System.out.println(gson2.toJson(book));

        try(
            InputStream bookStream =
                    this.getClass().getClassLoader().getResourceAsStream("book.json");
            InputStreamReader bookReader = new InputStreamReader(
                    Objects.requireNonNull(bookStream) )
        ) {
            book = gson.fromJson(bookReader, Book.class);
            System.out.println(book.getCard());
        }
        catch (IOException ex)
        {
            System.err.println(ex.getMessage());
        }

    }
    public  void run()
    {
        // Book book1 = new Book("D. Knuth", "Art of programming");
        // Book book2 = new Book("A. Eden", "Main principles of OOP");
        // System.out.println(book1.getCard());

//        Library library = new Library() ;
//        library.add( book1 ) ;
//        library.add( book2 ) ;
//        library.printAllCards() ;

        Library library = new Library() ;
        try
        {
            library.add(new Book("D. Knuth", "Art of programming"));
            library.add(new Book("Richter", ".NET"));
            library.add(new Newspaper("Newspaper Title", "2023-09-25"));
            library.add(new Journal("Journal Title", 13));
            library.add(new Newspaper("Washington Post", "2023-09-25"));
            library.add(new Journal("Amogus", 32));
            library.save();
        }
        catch (ParseException e)
        {
            System.err.println("Literature creation error : " + e.getMessage());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        // library.printAllCards();
        System.out.println("===================COPYABLE====================");
        library.printCopyable();
        System.out.println("=================NON-COPYABLE==================");
        library.printNonCopyable();
        System.out.println("===================PERIODIC====================");
        library.printPeriodic();
        System.out.println("=================NON-PERIODIC==================");
        library.printNonPeriodic();

    }
}


/*
Ресурси проєкту - папка "resources" (src/main/resources), файли з якої
за замовчанням копіюються у збірку (target/classes). Це гарантує
наявність ресурсів у підсумковому (зібраному) проєкті.
getClassLoader(), викликаний на довільному типі з нашого проєкту
дозволить визначити розміщення папки класів, а відтак і ресурсів.
getResourceAsStream("book.json") - відкриває файл та дає Stream
 */
/*
Робота з пакетами. JSON
Бібліотеки класів (аналог DLL) у Java постачаються як .JAR файли
Для використання їх можливостей необхідно додавати ці файли до збірки
Альтернатива - автоматизоване управління пакетами IDE з їх підключенням
до проєктів за допомогою декларацій у pom.xml файлі (у секції <dependencies>)
Maven має репозиторій (https://mvnrepository.com/) - онлайн бібліотеку
залежностей, завантажження з якої відбувається засобами IDE.
 */
/*
Бібліотека
Моделюємо книгосховище (бібліотеку) у якому є каталог (перелік наявних видань)
Видання є різного типу: книги, газети, жунали, тощо
Кожен тип має однакову картку у каталозі, у якій відзначається тип видання.

Абстрагування:
 Література - термін, що поєднує реальні сутності (книги, газети, жунали, тощо).
 Оскільки довільне видання повинно формувати картку для каталога, у цей клас
 (Література) має бути включений метод getCard(), але, оскільки до нього входить
 відомість про тип (який відомий тільки у конкретному об'єкті), цей метод може
 бути тільки абстрактний.
 Чи є у всіх "літератур" щось спільне (поле)? Так, це назва. Відповідно, її
 бажано закласти на рівень абстракції
 */
/*
ООП - об'єктно-орієнтована парадигма програмування
Програма - управління об'єктами та їх взаємодією
Програмування - налаштування об'єктів та зв'язків
Види зв'язків:
 - спадкування (наслідування)
 - асоціація
 - композиція
 - агрегація
 - залежність
 */
