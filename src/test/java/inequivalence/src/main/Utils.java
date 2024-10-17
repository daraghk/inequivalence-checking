package inequivalence.src.main;

import inequivalence.src.ParsedMethodSignature;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class Utils {
    public static void executeRandomParameterlessMethods(
            ArrayList<Integer> randomActions,
            List<ParsedMethodSignature> listOfParameterlessCommonMethodSignatures,
            HashMap<String, Method> parameterlessMethodsFromClassOne, Object classOneObject,
            HashMap<String, Method> parameterlessMethodsFromClassTwo, Object classTwoObject
    ) throws IllegalAccessException {
        // Iterate over the random method choices
        // Invoke them on the objects
        // Ensure the default contract
        for(int randomMethodChoice : randomActions){
            ParsedMethodSignature parameterlessMethodSignature = listOfParameterlessCommonMethodSignatures
                    .get(randomMethodChoice);

            try {
                Object returnValueFromClassOne = parameterlessMethodsFromClassOne
                        .get(parameterlessMethodSignature.getName())
                        .invoke(classOneObject, null);
                Object returnValueFromClassTwo = parameterlessMethodsFromClassTwo
                        .get(parameterlessMethodSignature.getName())
                        .invoke(classTwoObject, null);
                assertDefaultContract(returnValueFromClassOne, returnValueFromClassTwo, classOneObject, classTwoObject);
            }
            catch (InvocationTargetException e){
                System.out.println(e);
            }
        }
    }

    public static void assertDefaultContract(Object returnValueFromClassOne, Object returnValueFromClassTwo,
                                              Object classOneObject, Object classTwoObject) {
        // Ensure the return values are the same
        assertEquals(returnValueFromClassOne, returnValueFromClassTwo);

        // Ensure .equals() holds
        assertEquals(classOneObject, classTwoObject);
        assertEquals(classTwoObject, classOneObject);

        // Ensure .hashCode() holds
        assertEquals(classOneObject.hashCode(), classTwoObject.hashCode());
        assertEquals(classTwoObject.hashCode(), classOneObject.hashCode());

        // Ensure .toString() holds
        assertEquals(classOneObject.toString(), classTwoObject.toString());
        assertEquals(classTwoObject.toString(), classOneObject.toString());
    }

    public static HashMap<String, Method> getParameterlessMethodsForClass(
            Class classGiven,
            HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures
    ) {
        HashMap<String, Method> parameterlessFullMethodsFromClass = new HashMap<>();
        for (Method method : classGiven.getMethods()) {
            ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
            if (parameterlessCommonMethodSignatures.contains(parsedMethodSignature)) {
                parameterlessFullMethodsFromClass.put(method.getName(), method);
            }
        }
        return parameterlessFullMethodsFromClass;
    }
}
