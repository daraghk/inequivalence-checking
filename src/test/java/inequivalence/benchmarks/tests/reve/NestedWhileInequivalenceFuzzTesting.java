package inequivalence.benchmarks.tests.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.code.reve.NestedWhileInequivalence.nestedWhileNew;
import static inequivalence.benchmarks.code.reve.NestedWhileInequivalence.nestedWhileOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class NestedWhileInequivalenceFuzzTesting {

    @Fuzz
    public void nestedWhileComparison(int x, int g) {
        boolean originalResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = nestedWhileOriginal(x, g);
        } catch (Throwable e) {
            originalResultsInDivergence = true;
        }

        boolean newResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = nestedWhileNew(x, g);
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
