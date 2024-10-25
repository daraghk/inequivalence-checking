package inequivalence.benchmarks.tests.reve;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.runner.RunWith;

import static inequivalence.benchmarks.code.reve.AckermannInequivalence.ackermannNew;
import static inequivalence.benchmarks.code.reve.AckermannInequivalence.ackermannOriginal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assume.assumeTrue;

@RunWith(JQF.class)
public class AckermannInequivalenceFuzzTesting {

    @Fuzz
    public void ackermannComparison(int m, int n) {
        assumeTrue(m >= 0 && n >= 0);
        boolean originalAckermannResultsInDivergence = false;
        int originalResult = Integer.MIN_VALUE;
        try {
            originalResult = ackermannOriginal(m, n);
        } catch (Throwable e) {
            originalAckermannResultsInDivergence = true;
        }

        boolean newAckermannResultsinDivergence = false;
        int newResult = Integer.MIN_VALUE;
        try {
            newResult = ackermannNew(m, n);
        } catch (Throwable e) {
            newAckermannResultsinDivergence = true;
        }

        // Check the equivalence of the outputs
        assertEquals(originalResult, newResult);
        // Check the equivalence of divergence, want either both to diverge or not to diverge
        assertFalse(
                (originalAckermannResultsInDivergence && !newAckermannResultsinDivergence)
                        ||
                        (!originalAckermannResultsInDivergence && newAckermannResultsinDivergence)
        );
    }
}
