package inequivalence.benchmarks.bisims;

import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(JQF.class)
public class Ex3Point5InequivalenceFuzzTesting {

    static Ex3Point5Inequivalence originalObject;
    static Ex3Point5Inequivalence newObject;

    @BeforeClass
    public static void setUpObjects() {
        originalObject = new Ex3Point5Inequivalence(true);
        newObject = new Ex3Point5Inequivalence(true);
    }

    @Fuzz
    public void ex3point5Comparison(int dummy) {
        boolean originalResultsInDivergence = false;
        Boolean originalFunctionResult = null;
        try {
            originalFunctionResult = originalObject.firstExpression();
        } catch (Throwable e) {
            originalResultsInDivergence = true;
        }

        boolean newResultsinDivergence = false;
        Boolean newFunctionResult = null;
        try {
            newFunctionResult = newObject.secondExpression();
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
