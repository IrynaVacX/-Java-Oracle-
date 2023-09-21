package step.learning.basics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class FilesDemo {
    public void run()
    {
        File dir = new File("./");

        if (dir.exists())
        {
           System.out.println("Path exists");
        }
        else
        {
            System.out.println("Path does not exists");
        }

        System.out.printf("Path is %s %n",
                dir.isDirectory() ? "directory" : "file");
        System.out.println(dir.getAbsolutePath());

        for (String filename : dir.list())
        {
            System.out.println(filename);
        }


    }

    public void run_fileW()
    {
        File file = new File("./");
        String filename = "test.txt";

        try (OutputStream writer = new FileOutputStream(filename))
        {
            writer.write("Hello, World!".getBytes());
        }
        catch (IOException e) {
            System.out.println((e.getMessage()));
        }

        try (FileWriter writer = new FileWriter(filename, true))
        {
            writer.write("\nNew Line");
        }
        catch (IOException e) {
            System.out.println((e.getMessage()));
        }

        System.out.println("========================================");
        ///////////////////////////////////////////////////////////////
        
        StringBuilder sb = new StringBuilder();
        try (InputStream reader = new FileInputStream(filename))
        {
            int c;
            while( (c = reader.read() ) != -1 )
            {
                sb.append( (char)c );
            }
            System.out.println(sb.toString());
        } 
        catch (IOException e) {
            System.out.println((e.getMessage()));
        }

        System.out.println("========================================");
        ///////////////////////////////////////////////////////////////

        ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream(4096);
        byte[] buf = new byte[512];
        try (InputStream reader = new BufferedInputStream(
                                        new FileInputStream(filename)))
        {
            int cnt;
            while( (cnt = reader.read(buf) ) > 0 )
            {
                byteBuilder.write(buf, 0, cnt);
            }
            String content = new String(
                    byteBuilder.toByteArray(),
                    StandardCharsets.UTF_8 );

            System.out.println(content);
        }
        catch (IOException e) {
            System.out.println((e.getMessage()));
        }

        System.out.println("========================================");
        ///////////////////////////////////////////////////////////////

        try (InputStream reader = new FileInputStream(filename);
            Scanner scanner = new Scanner( reader ) )
        {
            while( scanner.hasNext() )
            {
                System.out.println(scanner.next());
            }
        }
        catch (IOException e) {
            System.out.println((e.getMessage()));
        }

        Scanner kbScanner = new Scanner( System.in );
        System.out.print("Your name : ");
        String name = kbScanner.next();
        System.out.printf("Hello, %s!%n", name);

        System.out.println("========================================");
        ///////////////////////////////////////////////////////////////

        Random random = new Random();
        // -HW-=-HW-=-HW- //
    }

    public void run_fs() throws IOException {
        File dir = new File("./uploads");

        if (dir.exists())
        {
            if (dir.isDirectory())
            {
                System.out.printf("Directory '%s' already exists%n", dir.getName());
            }
            else
            {
                System.out.printf("'%s' already exists BUT IS NOT DIRECTORY%n", dir.getName());
            }
        }
        else
        {
            if (dir.mkdir())
            {
                System.out.printf("Directory '%s' created", dir.getName());
            }
            else
            {
                System.out.printf("Directory '%s' creation error%n", dir.getName());
            }
        }

        File file = new File("./uploads/whitelist.txt");
        if (file.exists())
        {
            if(file.isFile())
            {
                System.out.printf("File '%s' already exists%n", file.getName());
            }
            else
            {
                System.out.printf("File '%s' already exists BUT IS NOT FILE%n", file.getName());
            }
        }
        else
        {
            try
            {
                if (file.createNewFile())
                {
                    System.out.printf("File '%s' created%n", file.getName());
                }
                else
                {
                    System.out.printf("File '%s' creation error%n", file.getName());
                }
            }
            catch (IOException ex)
            {
                if (file.createNewFile())
                {
                    System.out.printf("File '%s' created%n", file.getName());
                }
                else
                {
                    System.err.printf("File '%s' creation d%n", file.getName());
                }
            }
        }


    }
}
