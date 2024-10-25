package inequivalence.benchmarks.code.reve;

public class LimitTwoInequivalence {

    public static int limitTwoOriginal(int n) {
        int r;
        r = 0;

        if (n <= 0) {
            r = n;
        } else {
            r = limitTwoOriginal(n - 1);
            r = n + r;
        }

        return r;
    }

    public static int limitTwoNew(int n) {
        int r;
        r = 0;

        if (n <= 1) {
            r = n;
        } else {
            r = limitTwoNew(n - 1);
            r = n + r;
            if (n == 10) {
                r = 10;
            }
        }

        return r;
    }

}
