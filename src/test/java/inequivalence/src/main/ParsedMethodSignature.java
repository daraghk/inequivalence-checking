package inequivalence.src.main;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
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
        return Objects.equals(name, that.name) && Objects.equals(returnType, that.returnType)
                && Objects.equals(
                        new ParsedMethodParameters(this), new ParsedMethodParameters(that)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, returnType, new ParsedMethodParameters(this));
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
