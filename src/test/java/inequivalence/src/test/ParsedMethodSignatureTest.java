package inequivalence.src.test;

import inequivalence.src.main.ParsedMethodSignature;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.junit.Assert.assertEquals;

public class ParsedMethodSignatureTest {

    @Test
    public void getName() throws ClassNotFoundException {
        Class testClass = Class.forName("java.util.HashMap");
        Method testClassMethod = testClass.getMethods()[0];
        String testClassMethodName = testClassMethod.getName();

        ParsedMethodSignature methodSignature = new ParsedMethodSignature(testClassMethod);
        assertEquals(methodSignature.getName(), testClassMethodName);
    }

    @Test
    public void getReturnType() throws ClassNotFoundException {
        Class testClass = Class.forName("java.util.HashMap");
        Method testClassMethod = testClass.getMethods()[0];
        Class<?> testClassMethodReturnType = testClassMethod.getReturnType();

        ParsedMethodSignature methodSignature = new ParsedMethodSignature(testClassMethod);
        assertEquals(methodSignature.getReturnType(), testClassMethodReturnType);
    }

    @Test
    public void getParameters() throws ClassNotFoundException {
        Class testClass = Class.forName("java.util.HashMap");
        Method testClassMethod = testClass.getMethods()[0];
        Parameter[] testClassMethodParameters = testClassMethod.getParameters();

        ParsedMethodSignature methodSignature = new ParsedMethodSignature(testClassMethod);
        assertEquals(methodSignature.getParameters(), testClassMethodParameters);
    }

}