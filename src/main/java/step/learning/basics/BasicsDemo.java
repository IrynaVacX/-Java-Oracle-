package step.learning.basics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicsDemo {
    public void run()
    {
        System.out.println( "Hello World! (©️basics)" );
    }

    public void str_test()
    {
        String str1 = "Hello";
        String str2 = "Hello";
        String str3 = new String("Hello");

        if (str1 == str2)
        {
            System.out.println("str1 == str2");
        }
        else {
            System.out.println("str1 != str2");
        }

        if (str1 == str3)
        {
            System.out.println("str1 == str3");
        }
        else {
            System.out.println("str1 != str3");
        }

        if (str1.equals(str3))
        {
            System.out.println("str1 == str3");
        }
        else {
            System.out.println("str1 != str3");
        }
        System.out.println();
        System.out.println();
    }

    public void arr_test()
    {

        int[][] arr2d = {
                { 1, 2, 3 },
                { 4, 5, 6, 9},
                { 7, 8, 9, 1, 2}
        };
        for (int[] x : arr2d)
        {
            for (int y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }


    public void collections_test()
    {
        List<Integer> list1 = new ArrayList<>() ;
        list1.add(10);
        list1.add(20);
        list1.add(30);
        list1.add(40);

        for(Integer x : list1)
        {
            System.out.println(x + " ");
        }
        System.out.println();

        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "LocalHost");
        headers.put("Connection", "close");
        headers.put("Content-Type", "text/html");
        for (String key : headers.keySet())
        {
            System.out.println((
                    String.format(
                            "%s : %s",
                            key, headers.get(key)
                    )
                    ) );
        }

    }


}
