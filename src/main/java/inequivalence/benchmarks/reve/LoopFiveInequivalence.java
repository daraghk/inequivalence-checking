package inequivalence.benchmarks.reve;

public class LoopFiveInequivalence {

    public static int loopFiveOriginal(int n) {
        int i;
        int j;
        i = 0;
        j = 0;
        while (i < n + n) {
            j++;
            i++;
        }
        return j;
    }

    public static int loopFiveNew(int n) {
        int i;
        int j;
        i = n + 1;
        j = 0;
        while (i > 0) {
            j = j + 2;
            i = i - 1;
        }
        return j;
    }

}
