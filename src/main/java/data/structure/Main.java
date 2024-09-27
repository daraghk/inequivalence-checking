package data.structure;

import java.lang.reflect.*;
import java.util.*;

public class Main {
    public static void main(String[] args)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        String[] listOfMaps = {
                "java.util.HashMap",
                "java.util.Hashtable",
                "java.util.concurrent.ConcurrentHashMap",
                "java.util.concurrent.ConcurrentSkipListMap",
                "java.util.IdentityHashMap",
                "java.util.LinkedHashMap",
                "java.util.TreeMap",
                "java.util.WeakHashMap",
        };
        String[] listOfLists = {
                "java.util.ArrayList",
                "java.util.concurrent.CopyOnWriteArrayList",
                "java.util.LinkedList",
                "java.util.Stack",
        };
        int objectSize = 1000;
        int numberOfRandomActions = 100000;
        for(String classNameOneAsString : listOfMaps){
           for(String classNameTwoAsString : listOfMaps) {
               System.out.println("Comparing " + classNameOneAsString + " and " + classNameTwoAsString);
               executeRandomCommonParameterlessMethodsOnTwoClasses(
                       classNameOneAsString,
                       classNameTwoAsString,
                       objectSize,
                       numberOfRandomActions
               );
               System.out.println("Finished Comparison");
           }
        }
    }

    private static void executeRandomCommonParameterlessMethodsOnTwoClasses(
            String classOneNameString, String classTwoNameString, int objectSize, int numberOfRandomActions)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        // Create Classes for the data structures passed in on the command line
        Class classOne = Class.forName(classOneNameString);
        Class classTwo = Class.forName(classTwoNameString);

        // Create concrete instances of the Classes in question
        Constructor classOneConstructor = classOne.getConstructor();
        Constructor classTwoConstructor = classTwo.getConstructor();
        Object classOneObject = classOneConstructor.newInstance();
        Object classTwoObject = classTwoConstructor.newInstance();

        populateObjectsWithInitialIntegerData(classOneObject, classTwoObject, objectSize);

        // Collect the common method signatures and the parameterless common method signatures
        CommonMethodSignatures commonMethodSignatures = new CommonMethodSignatures(classOne.getMethods(),
                classTwo.getMethods());
        HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures = commonMethodSignatures
                .getParameterlessCommonMethodSignatures();

        // Collect the full parameterless methods for invocation from class one and two
        HashMap<String, Method> parameterlessFullMethodsFromClassOne = getParameterlessFullMethodsFromClas(classOne,
                parameterlessCommonMethodSignatures);
        HashMap<String, Method> parameterlessFullMethodsFromClassTwo = getParameterlessFullMethodsFromClas(classTwo,
                parameterlessCommonMethodSignatures);

        // Ensure that the full size method collections are the same size as the parameterless signature collection
        assert parameterlessFullMethodsFromClassOne.size() == parameterlessCommonMethodSignatures.size();
        assert parameterlessFullMethodsFromClassTwo.size() == parameterlessCommonMethodSignatures.size();

        ArrayList<Integer> randomActionChoices = getRandomActionsList(
                parameterlessCommonMethodSignatures, numberOfRandomActions);

        // Create a list of the parameterlessCommonMethodSignatures so that they can be chosen easily
        List<ParsedMethodSignature> listOfParameterlessCommonMethodSignatures = new ArrayList<>(
                parameterlessCommonMethodSignatures);

        // Iterate over the random method choices
        // Invoke them on the objects
        // Ensure the default contract
        for(int randomMethodChoice : randomActionChoices){
            ParsedMethodSignature parameterlessMethodSignature = listOfParameterlessCommonMethodSignatures
                    .get(randomMethodChoice);

            try {
                Object returnValueFromClassOne = parameterlessFullMethodsFromClassOne
                    .get(parameterlessMethodSignature.getName())
                    .invoke(classOneObject, null);
                Object returnValueFromClassTwo = parameterlessFullMethodsFromClassTwo
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

    private static void populateObjectsWithInitialIntegerData(
            Object classOneObject, Object classTwoObject, int objectSize)
            throws InstantiationException {
        // Populate the objects with rising set of integers
        if (classOneObject instanceof Map<?,?> && classTwoObject instanceof Map<?,?>){
            Map<Integer, Integer> classOneObjectMap = (Map<Integer, Integer>) classOneObject;
            Map<Integer, Integer> classTwoObjectMap = (Map<Integer, Integer>) classTwoObject;
            // If the objects are Maps then populate them with put methods
            for (int i = 0; i < objectSize; i++){
                classOneObjectMap.put(i, i+1);
                classTwoObjectMap.put(i, i+1);
            }
        }
        else if (classOneObject instanceof Collection<?> && classTwoObject instanceof Collection<?>){
            // If the objects are Collections then populate them with add methods
            Collection<Integer> classOneObjectCollection = (Collection<Integer>) classOneObject;
            Collection<Integer> classTwoObjectCollection = (Collection<Integer>) classTwoObject;
            // If the objects are Maps then populate them with put methods
            for (int i = 0; i < objectSize; i++){
                classOneObjectCollection.add(i);
                classTwoObjectCollection.add(i);
            }
        }
        else {
            throw new InstantiationException("Classes given are not maps or collections.");
        }
    }

    private static HashMap<String, Method> getParameterlessFullMethodsFromClas(
            Class classGiven, HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures) {
        HashMap<String, Method> parameterlessFullMethodsFromClassOne = new HashMap<>();
        for (Method method : classGiven.getMethods()) {
            ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
            if (parameterlessCommonMethodSignatures.contains(parsedMethodSignature)) {
                parameterlessFullMethodsFromClassOne.put(method.getName(), method);
            }
        }
        return parameterlessFullMethodsFromClassOne;
    }

    private static ArrayList<Integer> getRandomActionsList(
            HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures,
            int numberOfRandomActions) {
        // Create a sequence of random integers
        // These will be between 0 and the len(parameterlessCommonMethodSignatures)-1
        // This sequence will represent random actions to invoke on each object
        Random rand = new Random();
        ArrayList<Integer> randomActionChoices = new ArrayList<>();
        for (int i = 0; i < numberOfRandomActions; i++) {
            randomActionChoices.add(rand.nextInt(parameterlessCommonMethodSignatures.size()));
        }
        return randomActionChoices;
    }

    private static void assertDefaultContract(Object returnValueFromClassOne, Object returnValueFromClassTwo,
                                              Object classOneObject, Object classTwoObject) {
        // Ensure the return values are the same
        assert returnValueFromClassOne == returnValueFromClassTwo;

        // Ensure .equals() holds
        assert classOneObject.equals(classTwoObject);
        assert classTwoObject.equals(classOneObject);

        // Ensure .hashCode() holds
        assert classOneObject.hashCode() == classTwoObject.hashCode();
        assert classTwoObject.hashCode() == classOneObject.hashCode();

        // Ensure .toString() holds
        assert classOneObject.toString() == classTwoObject.toString();
        assert classTwoObject.toString() == classOneObject.toString();
    }
}