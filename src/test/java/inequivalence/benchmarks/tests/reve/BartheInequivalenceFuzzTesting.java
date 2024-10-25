package inequivalence.benchmarks.tests.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.code.reve.BartheInequivalence.bartheNew;
import static inequivalence.benchmarks.code.reve.BartheInequivalence.bartheOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class BartheInequivalenceFuzzTesting {

    @Fuzz
    public void bartheComparison(int m, int n) {
        boolean originalBartheResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = bartheOriginal(m, n);
        } catch (Throwable e) {
            originalBartheResultsInDivergence = true;
        }

        boolean newBartheResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = bartheNew(m, n);
        } catch (Throwable e){
            newBartheResultsinDivergence = true;
        }

        // Check the equivalence of the outputs
        assertEquals(originalResult, newResult);
        // Check the equivalence of divergence, want either both to diverge or not to diverge
        assertFalse(
                (originalBartheResultsInDivergence && !newBartheResultsinDivergence)
                        ||
                        (!originalBartheResultsInDivergence && newBartheResultsinDivergence)
        );
    }
}
