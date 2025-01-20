package agh.ics.oop.model;

public class AnimalTracker {
    private Animal trackedAnimal;
    private int deathDay = -1;

    public void startTracking(Animal animal) {
        this.trackedAnimal = animal;
        this.deathDay = -1;
    }

    public boolean isTracking() {
        return this.trackedAnimal != null;
    }

    public void updateDeathDay() {
        this.deathDay = trackedAnimal.getDeathDay();
    }

    public Animal getTrackedAnimal() {
        return trackedAnimal;
    }


    public int getPlantsEaten() {
        return trackedAnimal.getPlantsEaten();
    }

    public int getDescendantsCount() {
        return trackedAnimal.getDescendantsCount();
    }

    public int getDeathDay() {
        updateDeathDay();
        return deathDay;
    }
}
