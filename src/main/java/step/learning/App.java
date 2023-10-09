package step.learning;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.checkerframework.checker.units.qual.A;
import step.learning.async.AsyncDemo;
import step.learning.async.TaskDemo;
import step.learning.basics.BasicsDemo;
import step.learning.basics.FilesDemo;
import step.learning.homeworks.HW_01;
import step.learning.homeworks.HW_02;
import step.learning.ioc.ConfigModule;
import step.learning.ioc.IocDemo;
import step.learning.oop.OopDemo;

import java.io.IOException;
import java.text.ParseException;

public class App 
{
    public static void main( String[] args ) throws IOException, ParseException, InterruptedException {
        // HW-01
        // new HW_01().ShowDirectory("./");

        // HW-02
        // new HW_02().run();

        // OopDemo opd = new OopDemo();

        // HW-03
        // opd.run();

        // HW-04
        // opd.HW_04_run();

        // HW-05
        // opd.HW_05_run();

        // CW
        //opd.run2();
        //////opd.run();
        //opd.run3();
        /////opd.run4();

        // CW - 29/09/23
        // HW-07
        Injector injector = Guice.createInjector(new ConfigModule());
        IocDemo iocDemo = injector.getInstance(IocDemo.class);
        iocDemo.run();

        // CW - 02/09
        //iocDemo.run();

        //AsyncDemo ad = new AsyncDemo();
        //ad.run();

        //TaskDemo td = new TaskDemo();
        //td.run();

        // HW-08
        new AsyncDemo().HW_08();

        // HW-09
        new AsyncDemo().HW_09();
    }
}
