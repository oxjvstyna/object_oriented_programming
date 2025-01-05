package agh.ics.oop.model.util;

import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.model.MapChangeListener;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class FileMapDisplay implements MapChangeListener {
    private final String fileName;

    public FileMapDisplay(UUID mapId) {
        this.fileName = "map_" + mapId + ".log";
    }

    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("[" + LocalDateTime.now() + "]" + message + System.lineSeparator());

            String mapRepresentation = new MapVisualizer(worldMap).draw(worldMap.getCurrentBounds().lowerLeft(), worldMap.getCurrentBounds().upperRight());
            writer.write(mapRepresentation + System.lineSeparator());
        }catch (IOException e) {
            System.err.println("Błąd podczas zapisywania logów do pliku: " + e.getMessage());
        }
    }
}
