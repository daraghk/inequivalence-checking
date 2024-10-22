package inequivalence.src.main;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import inequivalence.src.CommonMethodSignatures;
import inequivalence.src.ParsedMethodSignature;
import org.junit.Assume;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.util.*;

import static inequivalence.src.main.Utils.executeRandomParameterlessMethods;
import static inequivalence.src.main.Utils.getParameterlessMethodsForClass;
import static org.junit.Assert.assertEquals;

@RunWith(JQF.class)
public class MapComparisons {

    private final String[] listOfMaps = {
            "java.util.HashMap",
            "java.util.Hashtable",
            "java.util.concurrent.ConcurrentHashMap",
            "java.util.concurrent.ConcurrentSkipListMap",
            "java.util.IdentityHashMap",
            "java.util.LinkedHashMap",
            "java.util.TreeMap",
            "java.util.WeakHashMap",
    };

    @Fuzz
    public void compareHashMapToLinkedHashMapUsingRandomParameterlessMethods(
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

    @Fuzz
    public void compareHashMapToTreeMapUsingRandomParameterlessMethods(
            HashMap<Integer, Integer> hashMap,
            ArrayList<Integer> randomActions
    ) throws
            ClassNotFoundException,
            IllegalAccessException
    {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.putAll(hashMap);

        // Create Classes for the data structures passed in
        Class classOne = Class.forName(hashMap.getClass().getName());
        Class classTwo = Class.forName(treeMap.getClass().getName());

        // Create Object types from the classes in question
        Object classOneObject = hashMap;
        Object classTwoObject = treeMap;

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

    @Fuzz
    public void compareHashMapToWeakHashMapUsingRandomParameterlessMethods(
            HashMap<Integer, Integer> hashMap,
            ArrayList<Integer> randomActions
    ) throws
            ClassNotFoundException,
            IllegalAccessException
    {
        WeakHashMap<Integer, Integer> weakHashMap = new WeakHashMap<>();
        weakHashMap.putAll(hashMap);

        // Create Classes for the data structures passed in
        Class classOne = Class.forName(hashMap.getClass().getName());
        Class classTwo = Class.forName(weakHashMap.getClass().getName());

        // Create Object types from the classes in question
        Object classOneObject = hashMap;
        Object classTwoObject = weakHashMap;

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

    @Fuzz
    public void compareHashMapToHashtableUsingRandomParameterlessMethods(
            HashMap<Integer, Integer> hashMap,
            ArrayList<Integer> randomActions
    ) throws
            ClassNotFoundException,
            IllegalAccessException
    {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        hashtable.putAll(hashMap);

        // Create Classes for the data structures passed in
        Class classOne = Class.forName(hashMap.getClass().getName());
        Class classTwo = Class.forName(hashtable.getClass().getName());

        // Create Object types from the classes in question
        Object classOneObject = hashMap;
        Object classTwoObject = hashtable;

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

    @Fuzz
    @Ignore
    public void compareHashMapToLinkedHashMapUsingRandomMethods(
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

        // Collect the full parameterless methods for invocation from class one and two
        // Todo: the equivalent will need to be done here for common methods with parameters also
        HashMap<String, Method> parameterlessMethodsFromClassOne = getParameterlessMethodsForClass(classOne,
                parameterlessCommonMethodSignatures);
        HashMap<String, Method> parameterlessMethodsFromClassTwo = getParameterlessMethodsForClass(classTwo,
                parameterlessCommonMethodSignatures);

        // Assert that the number of methods from each class is the same
        // Todo: change this to ensure that the size of the 'full' common method sets from each are equal
        assertEquals(parameterlessMethodsFromClassOne.size(), parameterlessMethodsFromClassTwo.size());

        // Create a list of the parameterlessCommonMethodSignatures so that they can be chosen easily
        // Todo: create this same list but also for methods that have parameters
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
}