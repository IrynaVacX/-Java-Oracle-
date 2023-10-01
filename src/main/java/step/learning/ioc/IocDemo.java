package step.learning.ioc;

import javax.inject.Inject;

public class IocDemo
{
    @Inject
    private HashService hashService;

    public void run()
    {
        System.out.println("IoC Demo");
        System.out.println( hashService.hash("IoC Demo") );
    }
}
