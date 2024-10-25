package inequivalence.benchmarks.code.reve;

public class InliningInequivalence {

    public static int inliningOriginal(int x) {
        if (x > 0) {
            x = inliningOriginal(x-1);
            x = x + 1;
        }
        if (x < 0) {
            x = 0;
        }
        return x;
    }

    public static int inliningNew(int x) {
        if (x > 1) {
            x = inliningNew(x-2);
            x = x + 2;
        }
        if (x < 2) {
            x = 0;
        }
        return x;
    }

}
