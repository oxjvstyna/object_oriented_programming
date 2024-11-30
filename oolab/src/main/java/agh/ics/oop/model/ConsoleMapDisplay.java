package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener{

    private int updateCount = 0;

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        updateCount++;
        System.out.print(worldMap);
        System.out.println("Mapa zaktualizowana: " + message);
        System.out.println("Sumaryczna liczba zmian: " + updateCount);
        System.out.println(); // dla przejrzystosci

    }
}
