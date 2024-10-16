package inequivalence.benchmarks.reve;

public class BartheInequivalence {

    public static int bartheOriginal(int n, int c) {
        int i;
        int j;
        int x;

        i = 0;
        j = 0;
        x = 0;

        while (i < n) {
            j = 5 * i + c;
            x = x + j;
            i++;
        }
        return x;
    }

    public static int bartheNew(int n, int c) {
        int i;
        int j;
        int x;

        i = 0;
        j = c;
        x = 0;

        while(i < n) {
            x = x + j;
            j = j + 5;
            if (i == 10) {
                j = 10;
            }
            i++;
        }
        return x;
    }

}
