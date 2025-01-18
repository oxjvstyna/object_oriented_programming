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

    public void stopTracking() {
        this.trackedAnimal = null;
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

    public void onAnimalDeath(int day) {
        if (isTracking() && trackedAnimal != null && !trackedAnimal.isAlive()) {
            this.deathDay = day;
        }
    }

    public String getStatus(int currentDay) {
        if (!isTracking() || trackedAnimal == null) {
            return "No animal is being tracked.";
        }

        return String.format(
                """
                Animal ID: %d
                Genome: %s
                Active Gene: %d
                Energy: %d
                Plants Eaten: %d
                Children Count: %d
                Descendants Count: %d
                Age: %d
                Death Day: %s
                """,
                trackedAnimal.getId(),
                trackedAnimal.getGenome(),
                trackedAnimal.getGenome().getGenes().get(trackedAnimal.getMoveIndex()),
                trackedAnimal.getEnergy(),
                plantsEaten,
                trackedAnimal.getNumberOfChildren(),
                descendantsCount,
                trackedAnimal.isAlive() ? currentDay - trackedAnimal.getAge() : -1,
                deathDay == -1 ? "Still alive" : deathDay
        );
    }
}
