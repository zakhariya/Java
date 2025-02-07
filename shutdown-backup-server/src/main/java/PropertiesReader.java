import lombok.Data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

@Data
public class PropertiesReader {
    private final java.net.URL FILE_URL = ClassLoader.getSystemResource("application.properties");
    public String url;
    public String token;

    public PropertiesReader() {
        Properties properties = new Properties();
        try  {
            properties.load(FILE_URL.openStream());

            this.url = properties.getProperty("url");
            this.token = properties.getProperty("token");
        } catch (FileNotFoundException fie) {
            fie.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> keys = properties.stringPropertyNames();

        for (String key : keys) {
            System.out.println(key + " - " + properties.getProperty(key));
        }
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }
}
