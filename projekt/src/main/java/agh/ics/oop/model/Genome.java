package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static agh.ics.oop.model.FullRandomness.mutate;

public class Genome {
    private final List<Integer> genes;

    public Genome(List<Integer> genes, int minMutation, int maxMutation) {
        this.genes = new ArrayList<>(genes);
        mutate(minMutation, maxMutation, this);
    }

    public Genome(int genomeLength) {
        this.genes = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < genomeLength; i++) {
            this.genes.add(rand.nextInt(8));
        }
    }

    public String[] getGenesAsStrings() {
        return this.genes.stream().map(String::valueOf).toArray(String[]::new);
    }

    public List<Integer> createChildGenome(Animal parent1, Animal parent2) {
        int energy1 = parent1.getEnergy();
        int energy2 = parent2.getEnergy();

        Animal strongerParent = energy1 > energy2 ? parent1 : parent2;
        Animal weakerParent = energy1 > energy2 ? parent2 : parent1;

        double totalEnergy = energy1 + energy2;
        double strongerRatio = strongerParent.getEnergy() / totalEnergy;

        List<Integer> strongerGenes = strongerParent.getGenomes().getGenes();
        List<Integer> weakerGenes = weakerParent.getGenomes().getGenes();

        if (strongerGenes.size() != weakerGenes.size()) { // do wywalenia
            throw new IllegalArgumentException("Parent genomes must have the same length.");
        }

        int genomeLength = strongerGenes.size();
        int strongerGenesCount = (int) (genomeLength * strongerRatio);
        int weakerGenesCount = genomeLength - strongerGenesCount;

        List<Integer> childGenotype = new ArrayList<>(genomeLength);

        Random random = new Random();
        boolean strongerStartsFromLeft = random.nextBoolean();

        if (strongerStartsFromLeft) {
            childGenotype.addAll(strongerGenes.subList(0, strongerGenesCount));
            childGenotype.addAll(weakerGenes.subList(genomeLength - weakerGenesCount, genomeLength));
        } else {
            childGenotype.addAll(weakerGenes.subList(0, weakerGenesCount));
            childGenotype.addAll(strongerGenes.subList(genomeLength - strongerGenesCount, genomeLength));
        }

        return childGenotype;
    }


    public List<Integer> getGenes() {
        return this.genes;
    }

    public int size() {
        return genes.size();
    }
}
