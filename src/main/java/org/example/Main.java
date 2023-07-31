package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileLoader fileLoader = new FileLoader();
        fileLoader.execute();
        fileLoader.validateDirectories();
    }
}
