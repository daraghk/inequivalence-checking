package inequivalence.bug.checking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class IsolatedBugCases {

    @Test
    public void testHashMapValuesAndLinkedHashMapValuesCase(){
        // Not sure if this is a bug.
        // Error arises in comparison between .values() between the two map types.
        // hashMap.values() -> Values type, linkedHashMap.values() -> LinkedValues type;
        // The above are not considered equal.
        // I think the values themselves are irrelevant.

        int[] errorCausingValues = new int[]
                {625966303, 0, -1077476197, 1112993854, -1023328640, 157542683, -1865719808, -1375677949};

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>();

        // Add keys and values to map, keys not interesting or part of error
        for(int i = 0; i < errorCausingValues.length; i++) {
            hashMap.put(i, errorCausingValues[i]);
            linkedHashMap.put(i, errorCausingValues[i]);
        }

        assertEquals(hashMap.values().getClass().getSimpleName(), linkedHashMap.values().getClass().getSimpleName());
        assertEquals(hashMap.values(), linkedHashMap.values());
    }
}
