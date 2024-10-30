package inequivalence.src.main;

import java.io.IOException;
import java.lang.reflect.*;
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
            HashMap<ParsedMethodSignature, Method> parameterlessMethodsFromClassOne, Object classOneObject,
            HashMap<ParsedMethodSignature, Method> parameterlessMethodsFromClassTwo, Object classTwoObject
    ) {
        for(int i = 0; i < randomActions.size(); i++){
            int randomMethodChoice = randomActions.get(i);
            ParsedMethodSignature parameterlessMethodSignature = listOfParameterlessCommonMethodSignatures
                    .get(randomMethodChoice);
            try {
                Object returnValueFromClassOne = parameterlessMethodsFromClassOne
                        .get(parameterlessMethodSignature)
                        .invoke(classOneObject, null);
                Object returnValueFromClassTwo = parameterlessMethodsFromClassTwo
                        .get(parameterlessMethodSignature)
                        .invoke(classTwoObject, null);

                InequivalenceType assertDefaultContractResult = assertDefaultContract(
                        returnValueFromClassOne, returnValueFromClassTwo, classOneObject, classTwoObject
                );

                // If the result is not null then an inequivalence has been detected, according to the default contract
                if(assertDefaultContractResult != null){
                    // Create the failing output trace
                    StringBuilder failingTraceOutput = createFailingOutputTrace(
                            assertDefaultContractResult,
                            randomActions,
                            listOfParameterlessCommonMethodSignatures,
                            classOneObject,
                            classTwoObject,
                            i
                    );
                    // Write the failing trace
                    writeFailingTraceToFile(failingTraceOutput.toString());
                    throw new AssertionError(failingTraceOutput);
                }
            }
            catch (InvocationTargetException | IOException | IllegalAccessException e){
                System.out.println(e);
            }
        }
    }

    // Iterate over the random method choices
    // Invoke them on the objects
    // Ensure the default contract
    public static void executeRandomMethodsWithParameters(
            List<Integer> randomActions,
            List<ParsedMethodSignature> listOfCommonMethodSignaturesWithParameters,
            HashMap<ParsedMethodSignature, Method> methodsWithParametersFromClassOne, Object classOneObject,
            HashMap<ParsedMethodSignature, Method> methodsWithParametersFromClassTwo, Object classTwoObject
    ) {
        for(int i = 0; i < randomActions.size(); i++){
            int randomMethodChoice = randomActions.get(i);
            ParsedMethodSignature methodSignatureWithParameters = listOfCommonMethodSignaturesWithParameters
                    .get(randomMethodChoice);

            // Create 'random' args like this for now
            ArrayList<Object> randomMethodArgs = new ArrayList<>();
            Random random = new Random();
            for (Parameter parameter : methodSignatureWithParameters.getParameters()){
                randomMethodArgs.add(random.nextInt());
            }
            try {
                Object returnValueFromClassOne = methodsWithParametersFromClassOne
                        .get(methodSignatureWithParameters)
                        .invoke(classOneObject, randomMethodArgs.toArray());
                Object returnValueFromClassTwo = methodsWithParametersFromClassTwo
                        .get(methodSignatureWithParameters)
                        .invoke(classTwoObject, randomMethodArgs.toArray());

                InequivalenceType assertDefaultContractResult = assertDefaultContract(
                        returnValueFromClassOne, returnValueFromClassTwo, classOneObject, classTwoObject
                );

                // If the result is not null then an inequivalence has been detected, according to the default contract
                if(assertDefaultContractResult != null){
                    // Create the failing output trace
                    StringBuilder failingTraceOutput = createFailingOutputTrace(
                            assertDefaultContractResult,
                            randomActions,
                            listOfCommonMethodSignaturesWithParameters,
                            classOneObject,
                            classTwoObject,
                            i
                    );
                    // Write the failing trace
                    writeFailingTraceToFile(failingTraceOutput.toString());
                    throw new AssertionError(failingTraceOutput);
                }
            }
            catch (InvocationTargetException | IOException | IllegalAccessException e){
                System.out.println(e);
            }
        }
    }

    private static InequivalenceType assertDefaultContract(
            Object returnValueFromClassOne,
            Object returnValueFromClassTwo,
            Object classOneObject,
            Object classTwoObject
    ) {
        // Ensure the return values from the invoked action / method are the same
        try {
            assertEquals(returnValueFromClassOne, returnValueFromClassTwo);
        } catch (AssertionError e) {
            return InequivalenceType.RETURN_VALUES_NOT_EQUAL;
        }

        // Ensure .equals() holds on the objects in question
        try {
            assertEquals(classOneObject, classTwoObject);
            assertEquals(classTwoObject, classOneObject);
        } catch (AssertionError e) {
            return InequivalenceType.OBJECTS_NOT_EQUAL;
        }

        // Ensure .hashCode() holds on the objects in question
        try {
            assertEquals(classOneObject.hashCode(), classTwoObject.hashCode());
            assertEquals(classTwoObject.hashCode(), classOneObject.hashCode());
        } catch (AssertionError e) {
            return InequivalenceType.HASHCODES_NOT_EQUAL;
        }

        // No assertion is thrown - the default contract holds
        return null;
    }

    public static HashMap<ParsedMethodSignature, Method> getFullMethodsFromCommonMethodSignatures(
            Class classGiven,
            HashSet<ParsedMethodSignature> commonMethodSignatures
    ) {
        HashMap<ParsedMethodSignature, Method> fullMethodsFromClass = new HashMap<>();
        for (Method method : classGiven.getMethods()) {
            ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
            if (commonMethodSignatures.contains(parsedMethodSignature)) {
                fullMethodsFromClass.put(parsedMethodSignature, method);
            }
        }
        return fullMethodsFromClass;
    }
}
