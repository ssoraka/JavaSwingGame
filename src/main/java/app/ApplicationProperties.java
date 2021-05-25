package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ApplicationProperties {
    private static Map<String, String> store;

    private static void readApplicationProperties() {
        InputStream resource = ApplicationProperties.class.getResourceAsStream("/application.properties");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int index = line.indexOf('=');
                if (index == -1) {
                    throw new RuntimeException("Not valid line: " + line);
                }

                store.put(line.substring(0, index).trim(), line.substring(index + 1).trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperties(String key) {
        if (store == null) {
            store = new HashMap<>();
            readApplicationProperties();
        }

        if (!store.containsKey(key)) {
            throw new RuntimeException("There is no properties " + key);
        }

        return store.get(key);
    }

    public static boolean propertyEqual(String key, String value) {
        if (store == null) {
            store = new HashMap<>();
            readApplicationProperties();
        }

        if (!store.containsKey(key)) {
            return false;
        }

        return store.get(key).equals(value);
    }
}

