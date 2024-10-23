package inequivalence.src.main.generators;

import java.util.ArrayList;
import java.util.List;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

public class RandomActionsGenerator extends Generator<List<Integer>> {

    @SuppressWarnings("unchecked")
    public RandomActionsGenerator(){
        super((Class<List<Integer>>) (Class) List.class);
    }

    // Todo: Regarding the value 20 below - is there some way to pass this value in from the test using this generator?
   @Override
   public List<Integer> generate(SourceOfRandomness random, GenerationStatus __ignore__) {
        int length = random.nextInt(0, 20);
        ArrayList<Integer> randomActions = new ArrayList<>();
        for(int i = 0; i < length; i++){
            randomActions.add(random.nextInt(0, 20));
        }
       return randomActions;
   }
}
