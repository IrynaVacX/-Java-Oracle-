package step.learning.homeworks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class HW_02
{
    public void run() throws IOException
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter maximum number of lines for the file : ");
        int maxLines;
        try
        {
            maxLines = scanner.nextInt();
        }
        catch (InputMismatchException e)
        {
            System.err.printf("Invalid value entered :: %s%n", e.getMessage());
            return;
        }
        Path filePath = GenerateFile(maxLines);

        Searching(filePath);
    }
    private Path GenerateFile(int maxLines) throws IOException
    {
        File file = new File("./uploads/fileRandomLines.txt");
        if (file.exists())
        {
            if(file.isFile())
            {
                System.out.printf("File '%s' already exists%n", file.getName());
            }
            else
            {
                System.out.printf("File '%s' already exists BUT IS NOT FILE%n", file.getName());
                return null;
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
                    return null;
                }
            }
            catch (IOException ex)
            {
                System.err.printf("File '%s' creation d%n", file.getName());
                return null;
            }
        }

        Random random = new Random();
        String arr_symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789  ";

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath()))
        {
            for (int i = 0; i < maxLines; i++)
            {
                StringBuilder str = new StringBuilder();

                for (int j = 0, r_ll = random.nextInt(50-10+1)+10; j < r_ll ; j++, r_ll = random.nextInt(50-10+1)+10)
                {
                    str.append(arr_symbols.charAt(random.nextInt(arr_symbols.length())));
                }

                writer.write(str.toString());
                writer.newLine();
            }
        }
        catch (IOException ex)
        {
            System.err.printf("Writing to the file '%s' failed%n", file.getName());
            return null;
        }

        return file.toPath();
    }
    private void Searching(Path filePath)
    {
        String longestLine = "";
        int longestLine_row = 0;

        try (BufferedReader reader = Files.newBufferedReader(filePath))
        {
            String line = reader.readLine();
            for(int i = 1; line != null; line = reader.readLine(), i++)
            {
                if (line.length() > longestLine.length())
                {
                    longestLine = line;
                    longestLine_row = i;
                }
            }
        }
        catch (IOException ex)
        {
            System.err.printf("Reading to the file '%s' failed%n", ex.getMessage());
            return;
        }

        System.out.printf(
                "Longest string number %s with length %S characters : %s",
                longestLine_row,
                longestLine.length(),
                longestLine );
        System.out.println();
    }
}
