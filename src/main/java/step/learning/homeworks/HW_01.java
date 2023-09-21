package step.learning.homeworks;

import java.io.File;
import java.text.SimpleDateFormat;

public class HW_01
{
    public static void ShowDirectory(String path)
    {
        File dir = new File(path);
        if (dir.exists())
        {
            if (dir.isDirectory())
            {
                File[] dirContents = dir.listFiles();
                if (dirContents != null)
                {
                    System.out.println("Directory contents :: " + dir.getAbsolutePath());
                    System.out.printf("%-15s %-25s %-10s %-20s%n", "Mode", "LastWriteTime", "Length", "Name");
                    System.out.println("-------------------------------------------------------------------");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                    for (File file : dirContents)
                    {
                        String mode = file.isDirectory() ? "d-----" : "-a----";
                        String lwt_date = dateFormat.format(file.lastModified());
                        long length = file.length();
                        String name = file.getName();

                        System.out.printf("%-15s %-25s %-10s %-20s%n", mode, lwt_date, (mode.equals("d-----") ? "" : length), name);
                    }
                }
                else
                {
                    System.out.println("DIRECTORY IS EMPTY");
                }
            }
            else
            {
                System.out.println("NOT A DIRECTORY");
            }
        }
        else
        {
            System.out.println("DOES NOT EXIST");
        }
    }
}
