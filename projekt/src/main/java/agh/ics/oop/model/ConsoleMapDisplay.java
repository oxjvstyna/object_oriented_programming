package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{

    private int updateCount = 0;

    @Override
    public synchronized void mapChanged(AbstractWorldMap worldMap, String message) {
        updateCount++;
        System.out.println("ID mapy: " + worldMap.getID());
        System.out.println("Mapa zaktualizowana: " + message);
        System.out.println("Sumaryczna liczba zmian: " + updateCount);
        System.out.println(); // dla przejrzystosci
    }
}
