package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class URLWriter {

    public void writeFile(List<String> values, String URLPath) {
        try (FileWriter writer = new FileWriter(URLPath)) {
            values.forEach(s -> {
                try {
                    writer.write(s + "\n");
                    System.out.println("file has been wrote successfully");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    public void writeFile(String URLPath, String info) {
        try (FileWriter writer = new FileWriter(URLPath)) {
            {
                try {
                    writer.write(info + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        System.out.println("file has been wrote successfully");
    }
}
