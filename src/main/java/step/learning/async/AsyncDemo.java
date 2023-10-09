package step.learning.async;

import jdk.nashorn.internal.runtime.NumberToString;

import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class AsyncDemo
{
    public void run()
    {
        System.out.println("Async Demo");
        // multiThreadDemo();
        int months = 12;
        Thread[] threads = new Thread[months];
        sum = 100.0;
        activeThreadsCount = months;
        for(int i = 0; i < months; i++)
        {
            threads[i] = new Thread( new MonthRate(i+1) );
            threads[i].start();
        }
//        try
//        {
//            for(int i = 0; i < months; i++)
//            {
//                threads[i].join();
//            }
//        }
//        catch (InterruptedException e)
//        {
//            throw new RuntimeException(e);
//        }
//        System.out.printf(Locale.US, "-------------%ntotal: %.2f%n", sum);
    }
    private Object sumLocker = new Object();
    private Object atcLocker = new Object();
    private int activeThreadsCount;
    private double sum;
    class MonthRate implements Runnable
    {
        private final int month;

        public MonthRate(int month)
        {
            this.month = month;
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ignore) { }

            double p = 0.1;
            double localSum;
            synchronized (sumLocker)
            {
                localSum = sum = sum * (p + 1.0);
            }
            System.out.printf(Locale.US, "month: %02d, percent: %.2f, sum: %.2f%n", month, p, localSum);

            synchronized (atcLocker)
            {
                activeThreadsCount--;
                if(activeThreadsCount == 0)
                {
                    System.out.printf(Locale.US, "-----------%ntotal: %.2f%n", sum);
                }
            }
        }
    }

    private void multiThreadDemo()
    {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run()
                    {
                        try
                        {
                            System.out.println("Thread starts");
                            Thread.sleep(1000);
                            System.out.println("Thread finishes");
                        }
                        catch (InterruptedException ex)
                        {
                            System.err.println("Sleeping broken :: " + ex.getMessage());
                        }
                    }
                }
        );
        thread.start();
        try
        {
            thread.join();
        }
        catch (InterruptedException ex)
        {
            System.err.println("Thread joining interrupted :: " + ex.getMessage());
        }
        System.out.println("multipleThreadDemo() finishes");

    }


    // HW-08
    public void HW_08()
    {
        System.out.println("---------HW-08---------");

        CountDownLatch latch = new CountDownLatch(3);

        Thread thread1 = new Thread(() -> {
            System.out.println("1 start");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.err.println("Sleep 1 error : " + e.getMessage());
            }
            System.out.println("1 finish");
            latch.countDown();
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("2 start");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.err.println("Sleep 2 error : " + e.getMessage());
            }
            System.out.println("2 finish");
            latch.countDown();
        });

        Thread thread3 = new Thread(() -> {
            System.out.println("3 start");
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                System.err.println("Sleep 3 error : " + e.getMessage());
            }
            System.out.println("3 finish");
            latch.countDown();
        });

        thread1.start();
        thread2.start();
        thread3.start();

        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            System.err.println("Error .await() : " + e.getMessage());
        }

        System.out.println("---final---");
    }

    // HW-09
    private final Object locker_hw_09 = new Object();
    private static final StringBuilder resultStrNum = new StringBuilder();
    public void HW_09()
    {
        System.out.println("---------HW-09---------");
        int limit = 9;
        for(int i = 0; i <= limit; i++)
        {
            final int num = i;
            Thread thread = new Thread(() -> {
                synchronized (locker_hw_09)
                {
                    resultStrNum.append(num);
                    if(num == limit)
                    {
                        System.out.println(resultStrNum);
                    }
                }
            });
            thread.start();
        }
    }
}