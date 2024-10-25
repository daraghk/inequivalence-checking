package inequivalence.benchmarks.code.reve;

public class NestedWhileInequivalence {

    public static int nestedWhileOriginal(int x, int g) {
        int i;
        i = 0;
        while (i < x) {
            i = i + 1;
            g = g - 2;
            g = g + 1;
            while(x < i) {
                x = x + 2;
                x = x - 1;
                g = g + 1;
            }
        }
        return g;
    }

    public static int nestedWhileNew(int x, int g) {
        int i;
        i = 0;
        while (i < x) {
            i = i + 1;
            g = g - 2;
            while(x < i) {
                x = x + 1;
                g = g + 2;
            }
        }
        return g;
    }

}
