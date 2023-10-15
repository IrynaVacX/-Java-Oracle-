package step.learning.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.*;

public class hwAnnualInflation
{
    private static final int monthsNumber = 12;
    private static final int maxThreads = 6;
    private final Object sumLocker;
    private final Object atcLocker;
    private int activeThreadCount;
    private static double sum;
    public hwAnnualInflation()
    {
        sumLocker = new Object();
        atcLocker = new Object();
        sum = 100;
    }

    public void run()
    {
        List<Future<Double>> results = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        for (int month = 1; month <= monthsNumber; month++)
        {
            Future<Double> res = executorService.submit(new InflationCalculator(month));
            results.add(res);
        }

        for (Future<Double> result : results)
        {
            try
            {
                result.get();
            }
            catch (ExecutionException | InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    public class InflationCalculator implements Callable<Double>
    {
        private final int month;
        public InflationCalculator(int month)
        {
            this.month = month;
        }

        @Override
        public Double call() throws Exception
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ignore) { }

            double localSum;
            double p = 0.1;

            synchronized (sumLocker)
            {
                localSum = sum = sum * (1 + p);
            }

            System.out.printf(Locale.US, "> month : %02d, percent : %.2f, sum : %.2f%n", month, p, localSum);

            synchronized (atcLocker)
            {
                activeThreadCount++;
                if (activeThreadCount == 12)
                {
                    System.out.printf(Locale.US, "=============%n> Total : %.2f%n", sum);
                }
            }

            return sum;
        }
    }
}
