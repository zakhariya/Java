import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PropertiesReader {
    private final java.net.URL FILE_URL = ClassLoader.getSystemResource("application.properties");

    public PropertiesReader() {
        Properties properties = new Properties();
        try  {
            properties.load(FILE_URL.openStream());
        } catch (FileNotFoundException fie) {
            fie.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.getProperty("host"));

        Set<String> keys = properties.stringPropertyNames();

        for (String key : keys) {
            System.out.println(key + " - " + properties.getProperty(key));
        }
    }



}
