package inequivalence.benchmarks.reve;

public class LimitOneInequivalence {

    public static int limitOneOriginal(int n) {
        int r;
        r = 0;

        if (n <= 1) {
            r = n;
        } else {
            r = limitOneOriginal(n - 1);
            r = n + r;
        }

        return r;
    }

    public static int limitOneNew(int n) {
        int r;
        r = 0;

        if (n <= 1) {
            r = n;
        } else {
            r = limitOneNew(n - 3);
            r = n + (n-1) + r;
        }

        return r;
    }

}
