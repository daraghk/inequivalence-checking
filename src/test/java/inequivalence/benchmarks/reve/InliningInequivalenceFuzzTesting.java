package inequivalence.benchmarks.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.reve.InliningInequivalence.inliningNew;
import static inequivalence.benchmarks.reve.InliningInequivalence.inliningOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class InliningInequivalenceFuzzTesting {

    @Fuzz
    public void inliningComparison(int x) {
        boolean originalInliningResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = inliningOriginal(x);
        } catch (Throwable e) {
            originalInliningResultsInDivergence = true;
        }

        boolean newInliningResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = inliningNew(x);
        } catch (Throwable e){
            newInliningResultsinDivergence = true;
        }

        // Check the equivalence of the outputs
        assertEquals(originalResult, newResult);
        // Check the equivalence of divergence, want either both to diverge or not to diverge
        assertFalse(
                (originalInliningResultsInDivergence && !newInliningResultsinDivergence)
                        ||
                        (!originalInliningResultsInDivergence && newInliningResultsinDivergence)
        );
    }
}
