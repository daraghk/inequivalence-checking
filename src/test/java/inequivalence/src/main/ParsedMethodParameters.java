package inequivalence.src.main;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// This class is solely to ensure that .equals() and .hashCode() work correctly for 'parameters' in ParsedMethodSignature.
// I.e. to ensure that method parameters are compared only on their names and types and not any 'deeper' information.
// For example, without the below hashing method, 'put' methods between different map implementations (with the same
// return type, and parameters having the same names and types) will be seen as being un-equal because of a field
// on reflection's 'Method' type that refers back to the owning Classes fully qualified name.
public class ParsedMethodParameters {

    private final ParsedMethodSignature owningMethodSignature;
    private final Parameter[] parameters;

    public ParsedMethodParameters(ParsedMethodSignature owningParsedMethodSignature){
       this.owningMethodSignature = owningParsedMethodSignature;
       this.parameters = this.owningMethodSignature.getParameters();
    }

    @Override
    public boolean equals(Object obj) {
        ParsedMethodParameters that = (ParsedMethodParameters) obj;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        List<String> parameterNames = new ArrayList<>();
        List<Type> parameterTypes = new ArrayList<>();
        for (Parameter parameter : this.parameters){
            parameterNames.add(parameter.getName());
            parameterTypes.add(parameter.getType());
        }
        return Objects.hash(Arrays.hashCode(new List[]{parameterNames}), Arrays.hashCode(new List[]{parameterTypes}));
    }
}
