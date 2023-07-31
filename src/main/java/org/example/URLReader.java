package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class URLReader {
    private final List<String> URLs = new ArrayList<>();

    private final List<String> fileSizes = new ArrayList<>();

    private static final String fileSizeOpeningTag = "<TD align=\"right\">";

    private static final String fileSizeEndingTag = "б</TD>";

    private static final String CONFIG_PATH = "C:\\FileLoader\\outcomeURLsConfig.txt";

    // прочитать с конфига путь к директории с файлами отчётов
    private String getOutcomeReportsFilePath() throws IOException {
        Path path = Paths.get(CONFIG_PATH);
        return Files.readAllLines(path).get(0);
    }

    // получить количество файлов в директории
    private int getCountOfFiles() throws IOException {
        String directoryPath = getOutcomeReportsFilePath();
        return Objects.requireNonNull(new File(directoryPath).list()).length;
    }

    public int getCountOfFiles(String directory){
        return Objects.requireNonNull(new File(directory).list()).length;
    }

    // получить список файлов в директории с отчётом
    private List<String> getReportsList() throws IOException {
        return Stream.of(Objects.requireNonNull(new File(getOutcomeReportsFilePath()).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }

    // извлечь теги с ссылками и извлечь размеры файлов
    public List<String> readReport() throws IOException {
        List<String> files = getReportsList();
        for (int i = 0; i < getCountOfFiles(); i++) {
            URL URL = new URL("file:///" + getOutcomeReportsFilePath() + "\\" + files.get(i));

            try (BufferedReader br = new BufferedReader(new InputStreamReader(URL.openStream(), "cp1251"));) {
                String searchable;
                while ((searchable = br.readLine()) != null) {
                    // FIXME продумать условие получше, т.к. ссылка на софт в каждом отчёте единична.
                    if (searchable.contains("a href") && !searchable.contains("http://www.10-strike.com/rus/network-file-search/")) {
                        String result = returnQuotationString(searchable);
                        URLs.add(result);
                    }
                    if (searchable.contains("<TD align=\"right\">") && searchable.contains("б</TD>")) {
                        String value = searchable.substring(fileSizeOpeningTag.length() + 2, searchable.length() - fileSizeEndingTag.length());
                        fileSizes.add(value);
                    }
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (int i = 0; i < URLs.size(); i++) {
            System.out.println(i + " - " + URLs.get(i));
            System.out.println(i + " His filesize - " + fileSizes.get(i) + " bytes");
        }
        return URLs;
    }

    // получить имена файлов
    public List<String> getFileNames() {
        return URLs.stream().map(s -> s.substring(s.lastIndexOf("\\") + 1))
                .collect(Collectors.toList());
    }

    // получить имена корневых каталогов
    public List<String> getPCNames() {
        List<String> PCNames = new ArrayList<>();
        for (String url : URLs) {
            String[] splittedString = url.split("\\\\");
            PCNames.add(splittedString[2]);
        }
        return PCNames;
    }

    //извлечь путь к файлу из кавычек
    private String returnQuotationString(String searchable) {
        List<Integer> values = new ArrayList<>();
        char quotation = '"';
        char[] charString = searchable.toCharArray();
        for (int i = 0; i < searchable.length(); i++) {
            if (charString[i] == quotation) {
                values.add(i);
            }
        }
        return searchable.substring(values.get(0) + 1, values.get(1));
    }

    //сконвертировать полученные размеры файлов из String в Integer
    public List<Integer> getFileSizes(){
        return fileSizes.stream().map(s->s.replaceAll(" ",""))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

}
