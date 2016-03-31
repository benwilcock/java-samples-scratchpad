package samples;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by ben on 30/03/16.
 */

public class SlightlyMoreComplexLambdasDemo {


    private static List<String> names;

    @BeforeClass
    public static void setup(){
        names = Arrays.asList("William", "Bob", "Aaron", "Bryan", "Brian");
    }

    @Test
    public void compareWithInline(){
        System.out.println("TestLambdasAgain.compareWithInline");

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        dumpNames();
    }

    @Test
    public void compareWithLambda(){
        System.out.println("TestLambdasAgain.compareWithLambda");
        Collections.sort(names, (o1, o2) -> o2.compareTo(o1));
        dumpNames();
    }

    @Test
    public void shouldMatchAaronWithInlineMatcher() {
        System.out.println("TestLambdasAgain.shouldMatchAaronWithInlineMatcher");
        for (String name : names) {
            if(match(name, new Predicate<String>() {
                @Override
                public boolean test(String s) {
                    return s.equals("Aaron");
                }
            })){
                System.out.println("Found " + name);
                return;
            }
        }
        fail();
    }

    @Test
    public void shouldMatchAaronWithLambdaAndPredicate() {
        System.out.println("TestLambdasAgain.shouldMatchAaronWithLambdaAndPredicate");
        for (String name : names) {
            if(match(name, (s) -> s.equals("Aaron"))){
                System.out.println("Found " + name);
                return;
            }
        }
        fail();
    }

    @Test
    public void shouldMatchAaronWithLambdAndPredicateUsingStream() {
        System.out.println("TestLambdasAgain.shouldMatchAaronWithLambdAndPredicateUsingStream");
        Optional<String> aaron = names.stream().filter((String s) -> s.equals("Aaron")).findFirst();
        assertTrue(aaron.isPresent());
        System.out.println("Found " + aaron.get());
    }


    private boolean match(String name, Predicate<String> predicate){
        return predicate.test(name);
    }
    private static void dumpNames(){
        String theNames = "";
        for (String name : names) {
            theNames = theNames + " " + name;
        }
        System.out.println("Names: " + theNames);
    }
}
