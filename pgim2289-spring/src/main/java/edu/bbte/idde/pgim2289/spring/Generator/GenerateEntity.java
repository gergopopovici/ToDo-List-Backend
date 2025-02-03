package edu.bbte.idde.pgim2289.spring.Generator;

import edu.bbte.idde.pgim2289.spring.CustomConfig.EntityConfig;
import edu.bbte.idde.pgim2289.spring.model.TokenEntity;
import edu.bbte.idde.pgim2289.spring.repository.repo.TokenEntityRepo;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Data
public class GenerateEntity {
    private final EntityConfig entityConfig;
    private final TokenEntityRepo tokenEntityRepo;

    public GenerateEntity(EntityConfig entityConfig, TokenEntityRepo tokenEntityRepo) {
        this.entityConfig = entityConfig;
        this.tokenEntityRepo = tokenEntityRepo;
    }

    @Bean
    public GenerateEntity generate() {
        if (entityConfig.getTokenEntity()) {
            TokenEntity tokenEntity = new TokenEntity();
            tokenEntity.setEntityName("todospring");
            tokenEntity.setOperations("read");
            tokenEntity.setValue("alma");
            tokenEntityRepo.save(tokenEntity);
            tokenEntity.setEntityName("todospring");
            tokenEntity.setOperations("write");
            tokenEntity.setValue("alma2");
            tokenEntityRepo.save(tokenEntity);
        }
        return new GenerateEntity(entityConfig, tokenEntityRepo);
    }
}
