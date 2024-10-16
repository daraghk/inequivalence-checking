package inequivalence.benchmarks.app.bisims.paper;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class Ex3Point5Inequivalence {

    private AtomicBoolean flag;

    public Ex3Point5Inequivalence(boolean initialFlagValue){
        this.flag = new AtomicBoolean(initialFlagValue);
    }

    public boolean firstExpression() {
        Supplier<Boolean> v2 = () -> true;
        return v2.get();
    }

    public boolean secondExpression() {
        Supplier<Boolean> v2 = () -> {
            if (flag.get()) {
                flag.set(false);
                return true;
            } else {
                return false;
            }
        };

        return v2.get();
    }

}
