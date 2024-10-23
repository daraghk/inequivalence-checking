package inequivalence.src.main;

import inequivalence.src.ParsedMethodSignature;
import org.junit.ComparisonFailure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        StringBuilder currentTrace = new StringBuilder();
        currentTrace.append(randomActions + "\n");
        // System.out.println(randomActions);
        for(int i = 0; i < randomActions.size(); i++){
            int randomMethodChoice = randomActions.get(i);
            ParsedMethodSignature parameterlessMethodSignature = listOfParameterlessCommonMethodSignatures
                    .get(randomMethodChoice);
            currentTrace.append(parameterlessMethodSignature + "\n");
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
                    // Write the failing trace
                    String failingTrace = currentTrace.toString();
                    writeFailingTraceToFile(failingTrace);
                    throw new RuntimeException(e);
                }
            }
            catch (InvocationTargetException | IOException e){
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
        // Todo: do we want to compare string representations? This is susceptible to ordering
         assertEquals(classOneObject.toString(), classTwoObject.toString());
         assertEquals(classTwoObject.toString(), classOneObject.toString());
    }

    private static void writeFailingTraceToFile(String failingTrace) throws IOException {
        // Get the current date and use in the name of the failing trace file
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String today = dateFormat.format(new Date());

        // Find the current day's failing trace file or create it if it does not exist
        File failingTracesFile = new File(
                "failing-traces/" + today + "-failing-traces.txt"
        );
        failingTracesFile.getParentFile().mkdirs();
        failingTracesFile.createNewFile();

        // Append the failing trace to the current day's failing-trace file
        FileWriter fileWriter = new FileWriter(failingTracesFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(failingTrace);
        bufferedWriter.close();
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
