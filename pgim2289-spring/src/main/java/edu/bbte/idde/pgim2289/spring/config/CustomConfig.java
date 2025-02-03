package edu.bbte.idde.pgim2289.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomConfig {
    private boolean failOnDeleteMissing;
    private boolean isDeleteMissingFatal;
}
