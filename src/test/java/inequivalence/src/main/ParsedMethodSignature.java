package inequivalence.src.main;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

public class ParsedMethodSignature {

    private final String name;
    private final Class<?> returnType;
    private final Parameter[] parameters;

    public ParsedMethodSignature(Method method){
        this.name = method.getName();
        this.returnType = method.getReturnType();
        this.parameters = method.getParameters();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedMethodSignature that = (ParsedMethodSignature) o;
        return Objects.equals(name, that.name) && Objects.equals(returnType, that.returnType);
                // Todo: Clarify leaving this out, should two methods be considered equal if their parameters differ?
                //&& Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        // Todo: Again, should two methods be considered equal if their parameters differ?
        // return Objects.hash(name, returnType, Arrays.hashCode(parameters));
        return Objects.hash(name, returnType);
    }

    @Override
    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Parameter[] getParameters() {
        return parameters;
    }
}
