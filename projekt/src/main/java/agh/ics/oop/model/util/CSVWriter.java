package agh.ics.oop.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    private FileWriter writer;

    public CSVWriter(String filePath) throws IOException {
        this.writer = new FileWriter(filePath);
    }

    public void writeHeader(List<String> headers) throws IOException {
        writer.write(String.join(",", headers) + "\n");
    }

    public void writeRow(List<String> row) throws IOException {
        writer.write(String.join(",", row) + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}
