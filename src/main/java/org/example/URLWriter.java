import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class URLWriter {
    private static final String URLPath = "C:\\FileLoader\\URLs\\URLs.txt";
    public void writeFile(List<String> values) {
        try (FileWriter writer = new FileWriter(URLPath)) {
            values.forEach(s -> {
                try {
                    writer.write(s + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            writer.flush();
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        System.out.println("file has been wrote successfully");
    }
}
