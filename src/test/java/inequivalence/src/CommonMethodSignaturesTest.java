package inequivalence.src;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommonMethodSignaturesTest {

    @Test
    public void getCommonMethodSignaturesSameTestClasses() throws ClassNotFoundException {
        Class testClassOne = Class.forName("java.util.HashMap");
        Class testClassTwo = Class.forName("java.util.HashMap");

        List<ParsedMethodSignature> parsedMethodSignatureListForClassOne = new ArrayList<>();
        for (Method method : testClassOne.getMethods()){
            ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
            parsedMethodSignatureListForClassOne.add(parsedMethodSignature);
        }

        CommonMethodSignatures commonMethodSignatures = new CommonMethodSignatures(testClassOne.getMethods(),
                testClassTwo.getMethods());

        assertEquals(commonMethodSignatures.getCommonMethodSignatures().size(),
                parsedMethodSignatureListForClassOne.size());
        assertTrue(commonMethodSignatures.getCommonMethodSignatures().containsAll(parsedMethodSignatureListForClassOne));
    }

    @Test
    public void getCommonMethodSignaturesSameDifferentClasses() throws ClassNotFoundException {
        Class testClassOne = Class.forName("java.util.HashMap");
        Class testClassTwo = Class.forName("java.util.TreeMap");

        List<ParsedMethodSignature> parsedMethodSignatureListForClassOne = new ArrayList<>();
        for (Method method : testClassOne.getMethods()){
            ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
            parsedMethodSignatureListForClassOne.add(parsedMethodSignature);
        }

        CommonMethodSignatures commonMethodSignatures = new CommonMethodSignatures(testClassOne.getMethods(),
                testClassTwo.getMethods());

        assertTrue(commonMethodSignatures.getCommonMethodSignatures().size() <
                parsedMethodSignatureListForClassOne.size());
    }

    @Test
    public void getParameterlessCommonMethodSignaturesSameTestClasses() throws ClassNotFoundException {
        Class testClassOne = Class.forName("java.util.HashMap");
        Class testClassTwo = Class.forName("java.util.HashMap");
        List<String> methodsToAvoid = new ArrayList<>(
                List.of(new String[]{"wait", "notify", "notifyAll", "getClass", "clear"})
        );
        List<ParsedMethodSignature> parsedParameterlessMethodSignatureListForClassOne = new ArrayList<>();
        for (Method method : testClassOne.getMethods()){
            if(method.getParameters().length == 0 && !methodsToAvoid.contains(method.getName())){
                ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
                parsedParameterlessMethodSignatureListForClassOne.add(parsedMethodSignature);
            }
        }

        CommonMethodSignatures commonMethodSignatures = new CommonMethodSignatures(testClassOne.getMethods(),
                testClassTwo.getMethods());

        assertEquals(commonMethodSignatures.getParameterlessCommonMethodSignatures().size(),
                parsedParameterlessMethodSignatureListForClassOne.size());
        assertTrue(commonMethodSignatures.getParameterlessCommonMethodSignatures()
                .containsAll(parsedParameterlessMethodSignatureListForClassOne));
    }

    @Test
    public void getParameterlessCommonMethodSignaturesDifferentTestClasses() throws ClassNotFoundException {
        Class testClassOne = Class.forName("java.util.HashMap");
        Class testClassTwo = Class.forName("java.util.TreeMap");

        List<ParsedMethodSignature> parsedParameterlessMethodSignatureListForClassOne = new ArrayList<>();
        for (Method method : testClassOne.getMethods()){
            if(method.getParameters().length == 0){
                ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
                parsedParameterlessMethodSignatureListForClassOne.add(parsedMethodSignature);
            }
        }

        List<ParsedMethodSignature> parsedParameterlessMethodSignatureListForClassTwo = new ArrayList<>();
        for (Method method : testClassTwo.getMethods()){
            if(method.getParameters().length == 0){
                ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
                parsedParameterlessMethodSignatureListForClassTwo.add(parsedMethodSignature);
            }
        }

        CommonMethodSignatures commonMethodSignatures = new CommonMethodSignatures(testClassOne.getMethods(),
                testClassTwo.getMethods());

        assertTrue(parsedParameterlessMethodSignatureListForClassOne
                .containsAll(commonMethodSignatures.getParameterlessCommonMethodSignatures()));
        assertTrue(parsedParameterlessMethodSignatureListForClassTwo
                .containsAll(commonMethodSignatures.getParameterlessCommonMethodSignatures()));
    }
}