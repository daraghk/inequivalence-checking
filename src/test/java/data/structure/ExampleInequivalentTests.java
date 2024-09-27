package data.structure;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

@RunWith(JQF.class)
public class ExampleInequivalentTests {

    @Fuzz
    public void ackermannComparison(int m, int n){
//        assumeTrue(m >=0 && m < 10);
//        assumeTrue(n >=0 && n < 10);
        int originalResult = ackermannOriginal(m, n);
        int newResult = ackermannNew(m, n);
        assertNotEquals(originalResult, newResult);
    }

    int ackermannOriginal(int m, int n) {
        int r;
        int x;
        x = 0;
        r = 0;
        if (m == 0) {
            r = n + 1;
        } else { if (m > 0 && n == 0) {
            r = ackermannOriginal(m - 1, 1);
        } else {
            x = ackermannOriginal(m, n - 1);
            r = ackermannOriginal(m - 1, x);
        }}
        return r;
    }

    int ackermannNew(int m, int n) {
        int r;
        int x;
        x = 0;
        r = 0;
        if (m > 0 && n == 0) {
            r = ackermannNew(m - 1, 1);
        } else { if (m == 1) {
            r = n + 1;
        } else {
            x = ackermannNew(m, n - 1);
            r = ackermannNew(m - 1, x);
        }}
        return r;
    }

    @Fuzz
    public void addHornComparison(int m, int n){
//        assumeTrue(m >=0 && m < 10);
//        assumeTrue(n >=0 && n < 10);
        int originalResult = addHornOriginal(m, n);
        int newResult = addHornNew(m, n);
        assertNotEquals(originalResult, newResult);
    }

    int addHornOriginal(int i, int j)
    {
        int r;
        r = 0;

        if (i == 0) {
            r = j;
        } else {
            r = addHornOriginal(i - 1, j + 1);
        }

        return r;
    }

    int addHornNew(int i, int j)
    {
        int r;
        r = 0;

        if (i == 0) {
            r = j;
        } else {
            if (i == 1) {
                r = j + 1;
            } else { if (i == 2) {
                r = j;
            } else {
                r = addHornNew(i - 1, j + 1);
            }}
        }

        return r;
    }
}
