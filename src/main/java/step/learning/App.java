package step.learning;

import step.learning.basics.BasicsDemo;
import step.learning.basics.FilesDemo;
import step.learning.homeworks.HW_01;
import step.learning.homeworks.HW_02;
import step.learning.oop.OopDemo;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
        // HW-01
        // new HW_01().ShowDirectory("./");

        // HW-02
        new HW_02().run();

        // HW-03
        // new OopDemo().run();
    }
}
