package inequivalence.src.main.maps;

import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.generator.Size;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import inequivalence.src.main.CommonMethodSignatures;
import inequivalence.src.main.ParsedMethodSignature;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.util.*;

import static inequivalence.src.main.ExecutionUtils.*;
import static org.junit.Assert.assertEquals;

@RunWith(JQF.class)
public class HashMapHashtableComparison {

    @Fuzz
    public void compareUsingRandomParameterlessMethods(
            HashMap<Integer, Integer> hashMap,
            @Size(min=1, max=100) List<@InRange(min = "0", max = "20") Integer> randomActions
    ) throws
            ClassNotFoundException
    {
        Hashtable<Integer, Integer> hashtable = new Hashtable();
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
        HashMap<ParsedMethodSignature, Method> parameterlessMethodsFromClassOne = getFullMethodsFromCommonMethodSignatures(classOne,
                parameterlessCommonMethodSignatures);
        HashMap<ParsedMethodSignature, Method> parameterlessMethodsFromClassTwo = getFullMethodsFromCommonMethodSignatures(classTwo,
                parameterlessCommonMethodSignatures);

        // Assert that the number of parameterless methods from each class is the same
        assertEquals(parameterlessMethodsFromClassOne.size(), parameterlessMethodsFromClassTwo.size());

        // Create a list of the parameterlessCommonMethodSignatures so that they can be chosen easily
        List<ParsedMethodSignature> listOfParameterlessCommonMethodSignatures = new ArrayList<>(
                parameterlessCommonMethodSignatures);

        // Filter out the initial random 'actions' given that do not actually correspond to a parameterless method
        randomActions.removeIf(action -> action >= listOfParameterlessCommonMethodSignatures.size());

        executeRandomParameterlessMethods(
                randomActions,
                listOfParameterlessCommonMethodSignatures,
                parameterlessMethodsFromClassOne, classOneObject,
                parameterlessMethodsFromClassTwo, classTwoObject
        );
    }

    @Fuzz
    public void compareUsingRandomMethodsWithParameters(
            HashMap<Integer, Integer> hashMap,
            @Size(min=1, max=100) List<@InRange(min = "0", max = "20") Integer> randomActions
    ) throws
            ClassNotFoundException
    {
        Hashtable<Integer, Integer> hashtable = new Hashtable();
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
        HashSet<ParsedMethodSignature> commonMethodSignaturesWithParameters = commonMethodSignatures
                .getCommonMethodSignaturesWithParameters();

        // Collect the full methods for invocation from class one and two
        HashMap<ParsedMethodSignature, Method> methodsFromClassOne = getFullMethodsFromCommonMethodSignatures(classOne,
                commonMethodSignaturesWithParameters);
        HashMap<ParsedMethodSignature, Method> methodsFromClassTwo = getFullMethodsFromCommonMethodSignatures(classTwo,
                commonMethodSignaturesWithParameters);

        // Assert that the number of methods with parameters from each class is the same
        assertEquals(commonMethodSignaturesWithParameters.size(), methodsFromClassOne.size());
        assertEquals(commonMethodSignaturesWithParameters.size(), methodsFromClassTwo.size());
        assertEquals(methodsFromClassOne.size(), methodsFromClassTwo.size());

        // Create a listOfCommonMethodSignaturesWithParameters  so that they can be chosen easily
        List<ParsedMethodSignature> listOfCommonMethodSignaturesWithParameters = new ArrayList<>(
                commonMethodSignaturesWithParameters);

        // Filter out the initial random 'actions' given that do not actually correspond to a parameterless method
        randomActions.removeIf(action -> action >= listOfCommonMethodSignaturesWithParameters.size());

        executeRandomMethodsWithParameters(
                randomActions,
                listOfCommonMethodSignaturesWithParameters,
                methodsFromClassOne, classOneObject,
                methodsFromClassTwo, classTwoObject
        );
    }
}
