package edu.bbte.idde.pgim2289.spring.CustomConfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
public class EntityConfig {
    @Getter
    @Setter
    private Boolean tokenEntity;
}
