package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.CSVWriter;

import java.io.IOException;
import java.util.Arrays;

public class Simulation implements Runnable {
    protected SimulationConfig simConfig;
    protected CSVWriter csvWriter;
    private final AnimalTracker tracker;
    int day = 1;

    public Simulation(SimulationConfig config) {
        this.simConfig = config;
        this.tracker = new AnimalTracker();
        try {
            csvWriter = new CSVWriter("simulation_stats.csv");
            csvWriter.writeHeader(Arrays.asList("Day", "AliveAnimals", "Plants", "AverageEnergy", "AverageLifespan"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        simConfig.currentMap().initializeMap(simConfig.animalCount());
    }

    public SimulationConfig getSimConfig() {
        return simConfig;
    }

    public AnimalTracker getTracker() {
        return tracker;
    }

    public void runStep() {
        simConfig.currentMap().handleMap();
        saveDailyStats(day);
        day++;
    }

    private void saveDailyStats(int day) {
        try {
            int aliveAnimals = this.getAliveAnimalsCount();
            int plants = this.getPlantCount();
            double averageEnergy = this.getAverageEnergy();
            double averageLifespan = this.getAverageLifespan();

            csvWriter.writeRow(Arrays.asList(
                    String.valueOf(day),
                    String.valueOf(aliveAnimals),
                    String.valueOf(plants),
                    String.format("%.2f", averageEnergy),
                    String.format("%.2f", averageLifespan)
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAliveAnimalsCount() {
        return simConfig.currentMap().getAnimals().size();
    }

    public int getPlantCount() {
        return simConfig.currentMap().getPlants().size();
    }

    public double getAverageEnergy() {
        return simConfig.currentMap().getAverageEnergy();
    }

    public double getAverageLifespan() {
        return simConfig.currentMap().getAverageLifespan();
    }
}