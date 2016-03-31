package samples;

import org.junit.Test;

/**
 * Created by ben on 29/03/16.
 *
 * This test is for experimentation purposes only. There is no system under test
 * here, I'm just playing about with new Java 8 features.
 */

public class SimpleLambdasDemo {

    interface Pretend {
        void doSomething(String message);
    }

    class UsesPretend {
        public void test(Pretend pretend, String message) {
            pretend.doSomething(message);
        }
    }
    @Test
    public void testWithoutLambdas() {

        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("TestLambdas.testWithoutLambdas");
            }
        };

        r1.run();

    }

    @Test
    public void testWithLambdas() {
        Runnable r1 = () -> System.out.println("TestLambdas.testWithLamdas");
        r1.run();
    }

    @Test
    public void testPretendInterface() {
        Pretend p1 = (message) -> System.out.println("TestLambdas.testPretendInterface [" + message + "]");
        p1.doSomething("test message");
    }

    @Test
    public void testUsesPretendClass() {

        new UsesPretend().test(
                (message) -> System.out.println("TestLambdas.testUsesPretendClass [" + message + "]"),
                "test message"
        );
    }
}
