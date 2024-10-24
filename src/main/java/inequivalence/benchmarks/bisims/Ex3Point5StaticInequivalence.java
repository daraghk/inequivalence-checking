package inequivalence.benchmarks.bisims;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class Ex3Point5StaticInequivalence {

    private static AtomicBoolean flag = new AtomicBoolean(true);

    public static boolean firstExpression() {
        Supplier<Boolean> v2 = () -> true;
        return v2.get();
    }

    public static boolean secondExpression() {
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
