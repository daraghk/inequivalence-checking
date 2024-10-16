package inequivalence.benchmarks.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.reve.LimitTwoInequivalence.limitTwoNew;
import static inequivalence.benchmarks.reve.LimitTwoInequivalence.limitTwoOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class LimitTwoInequivalenceFuzzTesting {

    @Fuzz
    public void limitTwoComparison(int x) {
        boolean originalResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = limitTwoOriginal(x);
        } catch (Throwable e) {
            originalResultsInDivergence = true;
        }

        boolean newResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = limitTwoNew(x);
        } catch (Throwable e){
            newResultsinDivergence = true;
        }

        // Check the equivalence of the outputs
        assertEquals(originalResult, newResult);
        // Check the equivalence of divergence, want either both to diverge or not to diverge
        assertFalse(
                (originalResultsInDivergence && !newResultsinDivergence)
                        ||
                        (!originalResultsInDivergence && newResultsinDivergence)
        );
    }
}
