import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class FileLoader {

    public static void main(String[] args) throws IOException {
        URLReader url = new URLReader();

        List<String> urls = url.readReport();
        List<String> fileNames = url.getFileNames();
        List<String> PCNames = url.getPCNames();

        for (int i = 0; i < urls.size(); i++) {
            try {
                Files.createDirectories(Paths.get("C:\\FileLoader\\reports\\" + PCNames.get(i)));
                loadFile("file:///" + urls.get(i), "C:\\FileLoader\\reports\\" + PCNames.get(i) + "\\" + fileNames.get(i));
                System.out.println("File " + fileNames.get(i) + " was downloaded successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        URLWriter writer = new URLWriter();
        writer.writeFile(urls);
    }

    //загрузить файл
    private static void loadFile(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1048576];
        int count = 0;
        while ((count = bis.read(buffer, 0, buffer.length)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

}