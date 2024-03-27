package logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingController {
    private final String configPath = "config.properties";
    private Properties properties;

    public SettingController() {
        properties = new Properties();
        loadSettings();
    }

    public void saveSettings(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream fos = new FileOutputStream(configPath)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        try (FileInputStream fis = new FileInputStream(configPath)) {
            properties.load(fis);
        } catch (IOException e) {
            // 파일이 존재하지 않을 경우 초기 설정을 사용할 수 있습니다.
            System.out.println("No existing settings, use default settings.");
        }
    }

    public String getSetting(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void initializeSettings() {
        properties.setProperty("screenSize", "small");
        try (FileOutputStream fos = new FileOutputStream(configPath)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
