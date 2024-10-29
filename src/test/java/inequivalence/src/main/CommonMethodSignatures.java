package inequivalence.src.main;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommonMethodSignatures {

    private final HashSet<ParsedMethodSignature> methodSignaturesClassOne;
    private final HashSet<ParsedMethodSignature> methodSignaturesClassTwo;
    private final HashSet<ParsedMethodSignature> commonMethodSignatures;
    private final HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures;
    private final HashSet<ParsedMethodSignature> commonMethodSignaturesWithParameters;

    private final List<String> parameterlessMethodToAvoid = new ArrayList<>(
            List.of(new String[]{"wait", "notify", "notifyAll", "getClass", "clear"})
    );

    public CommonMethodSignatures(Method[] methodsClassOne, Method[] methodsClassTwo){
        this.methodSignaturesClassOne = getParsedMethodSignatures(methodsClassOne);
        this.methodSignaturesClassTwo = getParsedMethodSignatures(methodsClassTwo);
        this.commonMethodSignatures = collectCommonMethodSignature();
        // Needs to be constructed after the common method signatures are collected
        this.parameterlessCommonMethodSignatures = collectParameterlessCommonMethodSignature();
        this.commonMethodSignaturesWithParameters = collectCommonMethodSignaturesWithParameters();
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
        HashSet<ParsedMethodSignature> parameterlessCommonMethodSignatures = new HashSet<>();
        for (ParsedMethodSignature methodSignature : this.commonMethodSignatures){
            if(methodSignature.getParameters().length == 0
                    && !this.parameterlessMethodToAvoid.contains(methodSignature.getName())){
                parameterlessCommonMethodSignatures.add(methodSignature);
            }
        }
        return parameterlessCommonMethodSignatures;
    }

    private HashSet<ParsedMethodSignature> collectCommonMethodSignaturesWithParameters(){
        HashSet<ParsedMethodSignature> commonMethodSignaturesWithPrimitiveParameters = new HashSet<>();
        for (ParsedMethodSignature methodSignature : this.commonMethodSignatures){
            // If the method has parameters
            if(methodSignature.getParameters().length > 0){
                commonMethodSignaturesWithPrimitiveParameters.add(methodSignature);
            }
        }
        return commonMethodSignaturesWithPrimitiveParameters;
    }

    public HashSet<ParsedMethodSignature> getCommonMethodSignatures() {
        return commonMethodSignatures;
    }

    public HashSet<ParsedMethodSignature> getParameterlessCommonMethodSignatures() {
        return parameterlessCommonMethodSignatures;
    }

    public HashSet<ParsedMethodSignature> getCommonMethodSignaturesWithParameters() {
        return commonMethodSignaturesWithParameters;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (ParsedMethodSignature methodSignature : this.commonMethodSignatures){
            stringBuilder.append(methodSignature + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
