package step.learning;

import step.learning.basics.BasicsDemo;
import step.learning.basics.FilesDemo;
import step.learning.homeworks.HW_01;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
//        BasicsDemo bd = new BasicsDemo();
//        bd.str_test();
//        bd.arr_test();
//        bd.collections_test();
//        FilesDemo fd = new FilesDemo();
//        fd.run();
//        fd.run_fs();
//        fd.run_fileW();
        new HW_01().ShowDirectory("./");
    }
}
