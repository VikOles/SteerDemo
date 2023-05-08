package utils.property;

import java.io.FileInputStream;
import java.util.Objects;
import java.util.Properties;

public final class ReadPropertyFile {
    private ReadPropertyFile() {
    }

    public static String getPropertyValue(String key) throws Exception {
        String value;
        Properties properties = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
        properties.load(file);
        value = properties.getProperty(key);
        if (Objects.isNull(value)) {
            throw new Exception("Property name " + key + " is not found. Please check config.properties");
        }
        return value;
    }

    public static int getIntValue(String key) throws Exception {
        return Integer.parseInt(getPropertyValue(key));

    }
}
