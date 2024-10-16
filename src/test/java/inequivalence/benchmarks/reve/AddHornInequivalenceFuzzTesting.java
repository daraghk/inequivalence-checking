package inequivalence.benchmarks.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.reve.AddHornInequivalence.addHornNew;
import static inequivalence.benchmarks.reve.AddHornInequivalence.addHornOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class AddHornInequivalenceFuzzTesting {

    @Fuzz
    public void addHornComparison(int m, int n) {
        boolean originalAddHornResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = addHornOriginal(m, n);
        } catch (Throwable e) {
            originalAddHornResultsInDivergence = true;
        }

        boolean newAddHornResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = addHornNew(m, n);
        } catch (Throwable e){
            newAddHornResultsinDivergence = true;
        }

        // Check the equivalence of the outputs
        assertEquals(originalResult, newResult);
        // Check the equivalence of divergence, want either both to diverge or not to diverge
        assertFalse(
                (originalAddHornResultsInDivergence && !newAddHornResultsinDivergence)
                        ||
                        (!originalAddHornResultsInDivergence && newAddHornResultsinDivergence)
        );
    }
}
