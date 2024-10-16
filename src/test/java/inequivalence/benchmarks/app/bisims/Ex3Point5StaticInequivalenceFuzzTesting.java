package inequivalence.benchmarks.app.bisims;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import inequivalence.benchmarks.app.bisims.Ex3Point5StaticInequivalence;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class Ex3Point5StaticInequivalenceFuzzTesting {

    @Fuzz
    public void ex3point5StaticComparison(int dummy) {
        boolean originalResultsInDivergence = false;
        Boolean originalFunctionResult = null;
        try {
            originalFunctionResult = Ex3Point5StaticInequivalence.firstExpression();
        } catch (Throwable e) {
            originalResultsInDivergence = true;
        }

        boolean newResultsinDivergence = false;
        Boolean newFunctionResult = null;
        try {
            newFunctionResult = Ex3Point5StaticInequivalence.secondExpression();
        } catch (Throwable e){
            newResultsinDivergence = true;
        }

        // Check the equivalence of the outputs
        assertEquals(originalFunctionResult, newFunctionResult);
        // Check the equivalence of divergence, want either both to diverge or not to diverge
        assertFalse(
                (originalResultsInDivergence && !newResultsinDivergence)
                        ||
                        (!originalResultsInDivergence && newResultsinDivergence)
        );
    }
}
