package inequivalence.src.main;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import inequivalence.src.CommonMethodSignatures;
import inequivalence.src.ParsedMethodSignature;
import org.junit.Assume;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(JQF.class)
public class MainTest {

    // Todo: How could the map parameter below be added at runtime such that JQF can create random instances?
    @Fuzz
    public void testCompareHashMap(
            HashMap<Integer, Integer> hashMap,
            ArrayList<Integer> randomActions
    ) throws
            ClassNotFoundException,
            IllegalAccessException
    {
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.putAll(hashMap);

        // Create Classes for the data structures passed in
        Class classOne = Class.forName(hashMap.getClass().getName());
        Class classTwo = Class.forName(linkedHashMap.getClass().getName());

        // Create Object types from the classes in question
        Object classOneObject = hashMap;
        Object classTwoObject = linkedHashMap;

        // Collect the common method signatures and the parameterless common method signatures
        CommonMethodSignatures commonMethodSignatures = new CommonMethodSignatures(
                classOne.getMethods(),
                classTwo.getMethods());
        HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures = commonMethodSignatures
                .getParameterlessCommonMethodSignatures();

        // Collect the full parameterless methods for invocation from class one and two
        HashMap<String, Method> parameterlessMethodsFromClassOne = getParameterlessMethodsForClass(classOne,
                parameterlessCommonMethodSignatures);
        HashMap<String, Method> parameterlessMethodsFromClassTwo = getParameterlessMethodsForClass(classTwo,
                parameterlessCommonMethodSignatures);

        // Assert that the number of parameterless methods from each class is the same
        assertEquals(parameterlessMethodsFromClassOne.size(), parameterlessMethodsFromClassTwo.size());

        // Create a list of the parameterlessCommonMethodSignatures so that they can be chosen easily
        List<ParsedMethodSignature> listOfParameterlessCommonMethodSignatures = new ArrayList<>(
                parameterlessCommonMethodSignatures);

        // Create the necessary assumptions about the values in randomActions
        Assume.assumeTrue(randomActions.size() >= 0);
        randomActions.stream()
                .map(integer ->
                        integer.intValue() >= 0
                                && integer.intValue() < listOfParameterlessCommonMethodSignatures.size())
                .forEach(Assume::assumeTrue);

        executeRandomParameterlessMethods(
                randomActions,
                listOfParameterlessCommonMethodSignatures,
                parameterlessMethodsFromClassOne, classOneObject,
                parameterlessMethodsFromClassTwo, classTwoObject
        );
    }

    private static void executeRandomParameterlessMethods(
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
                // e.printStackTrace();
                System.out.println(e);
            }
        }
    }

    private static void assertDefaultContract(Object returnValueFromClassOne, Object returnValueFromClassTwo,
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

    private static HashMap<String, Method> getParameterlessMethodsForClass(
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