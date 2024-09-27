package data.structure;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.Assert.*;

// This sets of tests forms a basis for testing the ParsedClass class
// It mostly compares the parsed class result vs. the actual reflection results for HashMap
// These be mostly identical (when a direct comparison is possible)
public class ParsedClassTest {

    @Test
    public void getFields() throws ClassNotFoundException {
        Class testClass = Class.forName("java.util.HashMap");
        ParsedClass parsedTestClass = new ParsedClass(testClass);
        HashMap actualClassInstance = new HashMap();
        Field[] actualClassInstanceFields = actualClassInstance.getClass().getFields();
        assertEquals(parsedTestClass.getFields(), actualClassInstanceFields);
        assertEquals(parsedTestClass.getFields().length, actualClassInstanceFields.length);
    }

    @Test
    public void getMethods() throws ClassNotFoundException {
        Class testClass = Class.forName("java.util.HashMap");
        ParsedClass parsedTestClass = new ParsedClass(testClass);
        HashMap actualClassInstance = new HashMap();
        Method[] actualClassInstanceMethods = actualClassInstance.getClass().getMethods();
        assertEquals(parsedTestClass.getMethods(), actualClassInstanceMethods);
        assertEquals(parsedTestClass.getMethods().length, actualClassInstanceMethods.length);
    }

    @Test
    public void getMethodSignatures() throws ClassNotFoundException {
        Class testClass = Class.forName("java.util.HashMap");
        ParsedClass parsedTestClass = new ParsedClass(testClass);
        HashMap actualClassInstance = new HashMap();
        Method[] actualClassInstanceMethods = actualClassInstance.getClass().getMethods();
        assertEquals(parsedTestClass.getMethodSignatures().size(), actualClassInstanceMethods.length);
    }
}