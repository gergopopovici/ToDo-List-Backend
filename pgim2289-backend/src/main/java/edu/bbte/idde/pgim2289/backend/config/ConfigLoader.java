package edu.bbte.idde.pgim2289.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    public static Config loadConfig() throws IOException {
        String profile = System.getenv().getOrDefault("APP_PROFILE", "jdbc");
        ObjectMapper objectMapper = new ObjectMapper();
        String configFileName;

        if ("jdbc".equals(profile)) {
            configFileName = "config-jdbc.json";
        } else if ("inMemory".equals(profile) || "inmemory".equalsIgnoreCase(profile)) {
            configFileName = "config-inmemory.json";
        } else {
            throw new IOException("Unknown profile: " + profile);
        }

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(configFileName)) {
            if (inputStream == null) {
                throw new IOException("Configuration file not found: " + configFileName);
            }
            return objectMapper.readValue(inputStream, Config.class);
        }
    }
}
