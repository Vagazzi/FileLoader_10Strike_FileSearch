package org.example;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileLoader {
    URLReader url = new URLReader();
    URLWriter writer = new URLWriter();

    public void execute() throws IOException {

        List<String> urls = url.readReport();
        List<String> fileNames = url.getFileNames();
        List<String> PCNames = url.getPCNames();
        List<Integer> fileSizes = url.getFileSizes();

        Runtime runtime = Runtime.getRuntime();

        for (int i = 0; i < urls.size(); i++) {
            try {
                Files.createDirectories(Paths.get(Configs.REPORT_PATH + PCNames.get(i)));
                loadFile("file:///" + urls.get(i), Configs.REPORT_PATH + PCNames.get(i) + "\\" + fileNames.get(i), fileSizes.get(i));

                System.out.println("File " + fileNames.get(i) + " was downloaded successfully!");
                System.out.println("Free memory - " + runtime.freeMemory() + " Max memory  - " + runtime.maxMemory());

                writer.writeFile(Configs.LOG_PATH, "Free memory - " + runtime.freeMemory() + " Max memory  - " + runtime.maxMemory());
                writer.writeFile(Configs.LOG_PATH, "File " + fileNames.get(i) + " was downloaded successfully!");
            } catch (Exception e) {
                e.printStackTrace();

                writer.writeFile(e.getMessage(), Configs.LOG_PATH);
                writer.writeFile(Configs.LOG_PATH, "Free memory - " + runtime.freeMemory() + " Max memory  - " + runtime.maxMemory());
            }
        }
        writer.writeFile(urls, Configs.URL_PATH);
        System.out.println("IT'S OVER ANAKIN. All available files was loaded BTW");
        writer.writeFile(Configs.LOG_PATH, "IT'S OVER ANAKIN. All available files was loaded BTW\"");
    }

    //загрузить файл
    private void loadFile(String urlStr, String file, int bufferSize) throws IOException {
        URL url = new URL(urlStr);

        if (bufferSize <= 0) {
            throw new IOException("Invalid filesize, file will be not loaded");
        }

        try (BufferedInputStream bis = new BufferedInputStream(url.openStream());
             FileOutputStream fis = new FileOutputStream(file)) {

            byte[] buffer = new byte[bufferSize];
            int count = 0;

            while ((count = bis.read(buffer, 0, buffer.length)) != -1) {
                fis.write(buffer, 0, count);
            }
        }

        System.gc();

    }

    //очистить пустые директории
    public void validateDirectories() throws IOException {
        File file = new File(Configs.REPORT_PATH);
        String[] folders = file.list();
        for (String folder : folders) {
            String directoryPath = Configs.REPORT_PATH + folder;
            if (url.getCountOfFiles(directoryPath) == 0) {
                FileUtils.deleteDirectory(new File(directoryPath));
                System.out.println("Deleted directory: " + directoryPath);
            }
        }
    }
}

