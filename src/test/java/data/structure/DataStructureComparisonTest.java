package data.structure;

import org.junit.runner.RunWith;
import edu.berkeley.cs.jqf.fuzz.Fuzz;
import edu.berkeley.cs.jqf.fuzz.JQF;
import org.apache.commons.collections4.map.HashedMap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

@RunWith(JQF.class)
public class DataStructureComparisonTest {

    @Fuzz
    public void testCompareSets(List<String> randomStrings, int initialSetCapacity, int numberOfMethodCalls){
        assumeTrue(initialSetCapacity >= 0);
        assumeTrue(numberOfMethodCalls >= 0 && numberOfMethodCalls <= 100);
        assumeTrue(randomStrings.size() == numberOfMethodCalls);

        HashSet<String> hashSet = new HashSet<>(initialSetCapacity);
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>(initialSetCapacity);

        // Number of common set methods being used between the HashSet and TreeSet
        int numberOfRandomActions = 5;

        for (int i = 0; i < numberOfMethodCalls ; i++) {
            int randomActionChoice = ThreadLocalRandom.current().nextInt(0, numberOfRandomActions);
            if(randomActionChoice == 1) {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, randomStrings.size());
                String randomString = randomStrings.get(randomIndex);
                hashSet.add(randomString);
                linkedHashSet.add(randomString);
                assertTrue(hashSet.contains(randomString));
                assertTrue(linkedHashSet.contains(randomString));
                assertEquals(hashSet, linkedHashSet);
                assertEquals(linkedHashSet, hashSet);
            }
            else if (randomActionChoice == 2){
                int randomIndex = ThreadLocalRandom.current().nextInt(0, randomStrings.size());
                String randomString = randomStrings.get(randomIndex);
                hashSet.remove(randomString);
                linkedHashSet.remove(randomString);
                assertFalse(hashSet.contains(randomString));
                assertFalse(linkedHashSet.contains(randomString));
                assertEquals(hashSet, linkedHashSet);
                assertEquals(linkedHashSet, hashSet);
            }
            else if (randomActionChoice == 3) {
                hashSet.clear();
                linkedHashSet.clear();
                assertTrue(hashSet.isEmpty());
                assertTrue(linkedHashSet.isEmpty());
                assertEquals(hashSet, linkedHashSet);
                assertEquals(linkedHashSet, hashSet);
            }
            else if (randomActionChoice == 4){
                hashSet.addAll(linkedHashSet);
                linkedHashSet.addAll(hashSet);
                assertTrue(hashSet.containsAll(linkedHashSet));
                assertTrue(linkedHashSet.containsAll(hashSet));
                assertEquals(hashSet, linkedHashSet);
                assertEquals(linkedHashSet, hashSet);
            }
            else {
                Object[] hashSetAsArray = hashSet.toArray();
                Object[] treeSetAsArray = linkedHashSet.toArray();
                assertTrue(linkedHashSet.containsAll(List.of(hashSetAsArray)));
                assertTrue(hashSet.containsAll(List.of(treeSetAsArray)));
            }
        }
    }

    @Fuzz
    public void testCompareMaps(List<String> randomMapKeys, List<String> randomMapValues, int initialMapCapacity, int numberOfMethodCalls){
        assumeTrue(initialMapCapacity >= 0);
        assumeTrue(numberOfMethodCalls >= 0 && numberOfMethodCalls <= 100);
        assumeTrue(randomMapKeys.size() == numberOfMethodCalls);
        assumeTrue(randomMapValues.size() == numberOfMethodCalls);

        HashMap<String, String> hashMap = new HashMap<>(initialMapCapacity);
        HashedMap<String, String> apacheHashedMap = new HashedMap<>(initialMapCapacity);

        // Number of common set methods being used between the HashSet and TreeSet
        int numberOfRandomActions = 4;

        for (int i = 0; i < numberOfMethodCalls ; i++) {
            int randomActionChoice = ThreadLocalRandom.current().nextInt(0, numberOfRandomActions);
            if(randomActionChoice == 1) {
                int randomIndex = ThreadLocalRandom.current().nextInt(0, randomMapKeys.size());
                String randomKey = randomMapKeys.get(randomIndex);
                String randomValue = randomMapValues.get(randomIndex);
                hashMap.put(randomKey, randomValue);
                apacheHashedMap.put(randomKey, randomValue);
                assertTrue(hashMap.containsKey(randomKey) && hashMap.containsValue(randomValue));
                assertTrue(apacheHashedMap.containsKey(randomKey) && apacheHashedMap.containsValue(randomValue));
                assertEquals(hashMap, apacheHashedMap);
                assertEquals(apacheHashedMap, hashMap);
            }
            else if (randomActionChoice == 2){
                int randomIndex = ThreadLocalRandom.current().nextInt(0, randomMapKeys.size());
                String randomKey = randomMapKeys.get(randomIndex);
                hashMap.remove(randomKey);
                apacheHashedMap.remove(randomKey);
                assertFalse(hashMap.containsKey(randomKey));
                assertFalse(apacheHashedMap.containsKey(randomKey));
                assertEquals(hashMap, apacheHashedMap);
                assertEquals(apacheHashedMap, hashMap);
            }
            else if (randomActionChoice == 3) {
                hashMap.clear();
                apacheHashedMap.clear();
                assertTrue(hashMap.isEmpty());
                assertTrue(apacheHashedMap.isEmpty());
                assertEquals(hashMap, apacheHashedMap);
                assertEquals(apacheHashedMap, hashMap);
            }
            else if (randomActionChoice == 4){
                hashMap.putAll(apacheHashedMap);
                apacheHashedMap.putAll(hashMap);
                assertEquals(hashMap, apacheHashedMap);
                assertEquals(apacheHashedMap, hashMap);
            }
        }
    }
}
