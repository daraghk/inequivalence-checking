package data.structure;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ParsedClass {

    private final Class classToParse;
    private final Field[] classFields;
    private final Method[] classMethods;
    private HashMap<String, ParsedMethodSignature> methodSignatures;

    public ParsedClass(Class classToParse){
        this.classToParse = classToParse;
        this.classFields = classToParse.getFields();
        this.classMethods = classToParse.getMethods();
        setMethodSignatures();
    }

    public Field[] getFields(){
        return classFields;
    }

    public Method[] getMethods(){
        return classMethods;
    }

    public HashMap<String, ParsedMethodSignature> getMethodSignatures() {
        return methodSignatures;
    }

    private void setMethodSignatures(){
        methodSignatures = new HashMap<>();
        for (Method method : classMethods) {
            methodSignatures.put(method.getName(), new ParsedMethodSignature(method));
        }
    }
}
