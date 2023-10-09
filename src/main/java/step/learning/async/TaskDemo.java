package step.learning.async;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TaskDemo
{
    private final ExecutorService threadPool = Executors.newFixedThreadPool(3);
    public void run()
    {
        System.out.println("Task Demo");
        long t1 = System.nanoTime();

        Future<String> task1 = threadPool.submit(new Callable<String>()
        {
            @Override
            public String call() throws Exception
            {
                System.out.println("Task 1 starts");
                Thread.sleep(1000);
                return "Task 1 finish";
            }
        });

        for(int i = 0; i < 10; i++)
        {
            printNumber(i+1);
        }

        Future<String> supplyTask = CompletableFuture
                .supplyAsync( supplier )
                .thenApply( continuation )
                .thenApply( continuation2 );

        try
        {
            String res = task1.get();
            System.out.println(res);
            System.out.println(supplyTask.get());
        }
        catch (InterruptedException | ExecutionException e)
        {
            throw new RuntimeException(e);
        }
       // threadPool.shutdown();

        try
        {
            threadPool.shutdown();
            threadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            threadPool.shutdownNow();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        long t2 = System.nanoTime();

        System.out.println("Main finish " + (t2 - t1) / 1e9);
    }

    private final Supplier<String> supplier = () -> {
        System.out.println("Task supply");
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return "Task supply finish";
    };  // interface щось приймає але не повертає
    private final Supplier<String> supplier2 = new Supplier<String>() {
        @Override
        public String get() {
            return "-=-=-=-";
        }
    };
    //private Function<String, String> continuation = (str) -> (str) + " continued";
    private Function<String, String> continuation = str -> str + " continued";
    private Function<String, String> continuation2 = new Function<String, String>() {
        @Override
        public String apply(String s)
        {
            return s + " continuation2";
        }
    };
    //
    private final Consumer<String> acceptor = new Consumer<String>() {
        @Override
        public void accept(String s) {

        }
    };

    private Future<?> printNumber(int num)
    {
        return threadPool.submit(
                () -> {
                    try
                    {
                        System.out.println("Task for number " + num);
                        Thread.sleep(200);
                        System.out.println("Number : " + num);
                    }
                    catch (InterruptedException e)
                    {
                        System.err.println(e.getMessage());
                    }
                }
        );
    }

}
