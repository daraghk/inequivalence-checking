package inequivalence.benchmarks.tests.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.code.reve.LoopFiveInequivalence.loopFiveNew;
import static inequivalence.benchmarks.code.reve.LoopFiveInequivalence.loopFiveOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class LoopFiveInequivalenceFuzzTesting {

    @Fuzz
    public void loopFiveComparison(int x) {
        boolean originalResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = loopFiveOriginal(x);
        } catch (Throwable e) {
            originalResultsInDivergence = true;
        }

        boolean newResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = loopFiveNew(x);
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
