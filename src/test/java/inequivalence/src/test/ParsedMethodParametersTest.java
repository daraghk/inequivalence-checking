package inequivalence.src.test;

import inequivalence.src.main.ParsedMethodParameters;
import inequivalence.src.main.ParsedMethodSignature;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;

public class ParsedMethodParametersTest {

    @Test
    public void testTwoParameterSetsWithTheSameNamesAndTypesProduceTheSameHashCode() throws ClassNotFoundException {
       // Using 'put' method from HashMap and TreeMap, they share params with the same names and types
        Class hashMapClass = Class.forName("java.util.HashMap");
        Class treeMapClass = Class.forName("java.util.TreeMap");

        Optional<Method> putFromHashMap = Arrays.stream(hashMapClass.getMethods())
                .filter(method -> method.getName().equals("put"))
                .findAny();
        assertTrue(putFromHashMap.isPresent());

        Optional<Method> putFromTreeMap = Arrays.stream(treeMapClass.getMethods())
                .filter(method -> method.getName().equals("put"))
                .findAny();
        assertTrue(putFromTreeMap.isPresent());

        ParsedMethodSignature putFromHashMapMethodSignature = new ParsedMethodSignature(putFromHashMap.get());
        ParsedMethodSignature putFromTreeMapMethodSignature = new ParsedMethodSignature(putFromTreeMap.get());

        ParsedMethodParameters paramsFromHashMapPut = new ParsedMethodParameters(putFromHashMapMethodSignature);
        ParsedMethodParameters paramsFromTreeMapPut = new ParsedMethodParameters(putFromTreeMapMethodSignature);
        assertEquals(paramsFromTreeMapPut, paramsFromHashMapPut);
        assertEquals(paramsFromTreeMapPut.hashCode(), paramsFromHashMapPut.hashCode());
    }

    @Test
    public void testTwoParameterSetsWithDifferentNamesAndTypesProduceDifferentHashCodes() throws ClassNotFoundException {
        Class hashMapClassOne = Class.forName("java.util.HashMap");
        Class hashMapClassTwo = Class.forName("java.util.HashMap");

        // The below methods aren't overloaded for HashMap, the method matched with is unique
        Optional<Method> putFromHashMap = Arrays.stream(hashMapClassOne.getMethods())
                .filter(method -> method.getName().equals("put"))
                .findAny();
        assertTrue(putFromHashMap.isPresent());

        Optional<Method> getFromHashMap = Arrays.stream(hashMapClassTwo.getMethods())
                .filter(method -> method.getName().equals("get"))
                .findAny();
        assertTrue(getFromHashMap.isPresent());

        ParsedMethodSignature putFromHashMapMethodSignature = new ParsedMethodSignature(putFromHashMap.get());
        ParsedMethodSignature getFromHashMapMethodSignature = new ParsedMethodSignature(getFromHashMap.get());

        ParsedMethodParameters paramsFromHashMapPut = new ParsedMethodParameters(putFromHashMapMethodSignature);
        ParsedMethodParameters paramsFromHashMapGet = new ParsedMethodParameters(getFromHashMapMethodSignature);
        assertNotEquals(paramsFromHashMapGet, paramsFromHashMapPut);
    }
}
