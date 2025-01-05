package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.util.Collections.shuffle;

public class FullRandomness implements MutationVariant {

    protected static void mutate(int minMutation, int maxMutation, Genome genome) {
        Random random = new Random();
        int numberOfMutations = random.nextInt(minMutation, maxMutation + 1);

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < genome.getGenes().size(); i++) {
            indices.add(i);
        }
        shuffle(indices);

        for (int i = 0; i < numberOfMutations; i++) {
            int index = indices.get(i);
            genome.getGenes().set(index, random.nextInt(8));
        }
    }
}
