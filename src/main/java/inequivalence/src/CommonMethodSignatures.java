package inequivalence.src;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommonMethodSignatures {

    private final HashSet<ParsedMethodSignature> methodSignaturesClassOne;
    private final HashSet<ParsedMethodSignature> methodSignaturesClassTwo;
    private final HashSet<ParsedMethodSignature> commonMethodSignatures;
    private final HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures;

    public CommonMethodSignatures(Method[] methodsClassOne, Method[] methodsClassTwo){
        this.methodSignaturesClassOne = getParsedMethodSignatures(methodsClassOne);
        this.methodSignaturesClassTwo = getParsedMethodSignatures(methodsClassTwo);
        this.commonMethodSignatures = collectCommonMethodSignature();
        // Needs to be constructed after the common method signatures are collected
        this.parameterlessCommonMethodSignatures = collectParameterlessCommonMethodSignature();
    }

    private HashSet<ParsedMethodSignature> getParsedMethodSignatures(Method[] methods){
        HashSet<ParsedMethodSignature> parsedMethodSignatures = new HashSet<>();
        for (Method method : methods){
            ParsedMethodSignature parsedMethodSignature = new ParsedMethodSignature(method);
            parsedMethodSignatures.add(parsedMethodSignature);
        }
        return parsedMethodSignatures;
    }

    private HashSet<ParsedMethodSignature> collectCommonMethodSignature(){
        HashSet<ParsedMethodSignature> methodSignaturesClassOneCopy =
                (HashSet<ParsedMethodSignature>) methodSignaturesClassOne.clone();
        methodSignaturesClassOneCopy.retainAll(methodSignaturesClassTwo);
        return methodSignaturesClassOneCopy;
    }

    private HashSet<ParsedMethodSignature> collectParameterlessCommonMethodSignature(){
        List<String> methodsToAvoid = new ArrayList<>(
                List.of(new String[]{"wait", "notify", "notifyAll", "getClass", "clear"})
        );
        HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures = new HashSet<>();
        for (ParsedMethodSignature methodSignature : this.commonMethodSignatures){
            if(methodSignature.getParameters().length == 0
                    && !methodsToAvoid.contains(methodSignature.getName())){
                parameterlessCommonMethodSignatures.add(methodSignature);
            }
        }
        return parameterlessCommonMethodSignatures;
    }

    public HashSet<ParsedMethodSignature> getCommonMethodSignatures() {
        return commonMethodSignatures;
    }

    public HashSet<ParsedMethodSignature> getParameterlessCommonMethodSignatures() {
        return parameterlessCommonMethodSignatures;
    }
}
