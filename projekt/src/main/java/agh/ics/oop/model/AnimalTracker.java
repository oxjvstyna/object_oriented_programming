package agh.ics.oop.model;

public class AnimalTracker {
    private Animal trackedAnimal;
    private int plantsEaten = 0;
    private int descendantsCount = 0;
    private int deathDay = -1;

    public void startTracking(Animal animal) {
        this.trackedAnimal = animal;
        this.plantsEaten = 0;
        this.descendantsCount = 0;
        this.deathDay = -1;
    }

    public boolean isTracking() {
        return this.trackedAnimal != null;
    }

    public void onPlantEaten() {
        if (isTracking()) {
            plantsEaten++;
        }
    }

    public void onDescendantAdded() {
        if (isTracking()) {
            descendantsCount++;
        }
    }

    public void updateDeathDay() {
        this.deathDay = trackedAnimal.getDeathDay();
    }

    public Animal getTrackedAnimal() {
        return trackedAnimal;
    }


    public int getPlantsEaten() {
        return plantsEaten;
    }

    public int getDescendantsCount() {
        return descendantsCount;
    }

    public int getDeathDay() {
        updateDeathDay();
        return deathDay;
    }
}
