package data.structure;

public class DefaultPropertyContract {

    private final Object first;
    private final Object second;

    public DefaultPropertyContract(Object first, Object second){
        this.first = first;
        this.second = second;
    }

    public boolean assertDefaultProperties(){
        return assertEquals() && assertHashCode() && assertToString();
    }

    private boolean assertEquals(){
        return first.equals(second) && second.equals(first);
    }

    private boolean assertHashCode(){
        return first.hashCode() == second.hashCode() && second.hashCode() == first.hashCode();
    }

    private boolean assertToString(){
        return first.toString().equals(second.toString()) && second.toString().equals(first.toString());
    }
}
