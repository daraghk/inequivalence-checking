package inequivalence.src.main;

import inequivalence.src.ParsedMethodSignature;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static inequivalence.src.main.TraceWritingUtils.createFailingOutputTrace;
import static inequivalence.src.main.TraceWritingUtils.writeFailingTraceToFile;
import static org.junit.Assert.assertEquals;

public class ExecutionUtils {

    // Iterate over the random method choices
    // Invoke them on the objects
    // Ensure the default contract
    public static void executeRandomParameterlessMethods(
            List<Integer> randomActions,
            List<ParsedMethodSignature> listOfParameterlessCommonMethodSignatures,
            HashMap<String, Method> parameterlessMethodsFromClassOne, Object classOneObject,
            HashMap<String, Method> parameterlessMethodsFromClassTwo, Object classTwoObject
    ) throws IllegalAccessException {
        for(int i = 0; i < randomActions.size(); i++){
            int randomMethodChoice = randomActions.get(i);
            ParsedMethodSignature parameterlessMethodSignature = listOfParameterlessCommonMethodSignatures
                    .get(randomMethodChoice);
            try {
                Object returnValueFromClassOne = parameterlessMethodsFromClassOne
                        .get(parameterlessMethodSignature.getName())
                        .invoke(classOneObject, null);
                Object returnValueFromClassTwo = parameterlessMethodsFromClassTwo
                        .get(parameterlessMethodSignature.getName())
                        .invoke(classTwoObject, null);

                // In the 'catch' block, if the assertions in 'assertDefaultContract' fail then print the failing trace
                try {
                    assertDefaultContract(
                            returnValueFromClassOne, returnValueFromClassTwo, classOneObject, classTwoObject
                    );
                } catch (AssertionError e) {
                    // Create the failing output trace
                    StringBuilder failingTraceOutput = createFailingOutputTrace(
                            randomActions, listOfParameterlessCommonMethodSignatures, classOneObject, classTwoObject, i
                    );

                    // Write the failing trace
                    writeFailingTraceToFile(failingTraceOutput.toString());
                    throw new RuntimeException(e);
                }
            }
            catch (InvocationTargetException | IOException e){
                System.out.println(e);
            }
        }
    }

    private static void assertDefaultContract(Object returnValueFromClassOne, Object returnValueFromClassTwo,
                                              Object classOneObject, Object classTwoObject) {
        // Ensure the return values from the invoked action / method are the same
        assertEquals(returnValueFromClassOne, returnValueFromClassTwo);

        // Ensure .equals() holds on the objects in question
        assertEquals(classOneObject, classTwoObject);
        assertEquals(classTwoObject, classOneObject);

        // Ensure .hashCode() holds on the objects in question
        assertEquals(classOneObject.hashCode(), classTwoObject.hashCode());
        assertEquals(classTwoObject.hashCode(), classOneObject.hashCode());
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
