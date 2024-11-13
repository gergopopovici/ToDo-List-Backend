package edu.bbte.idde.pgim2289.backend.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.pgim2289.backend.config.Config;
import edu.bbte.idde.pgim2289.backend.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class DataSourceFactory {
    private static HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    public static synchronized HikariDataSource getDataSource() throws IOException {
        if (dataSource == null) {
            try {
                String profile = System.getenv().getOrDefault("APP_PROFILE", "jdbc");
                Config config = ConfigLoader.loadConfig(profile);
                dataSource = new HikariDataSource();
                logger.info("Creating HikariCP data source and connecting to the database");
                dataSource.setJdbcUrl(config.getJdbcUrl());
                dataSource.setUsername(config.getJdbcUser());
                dataSource.setPassword(config.getJdbcPassword());
                dataSource.setDriverClassName(config.getJdbcDriver());
                dataSource.setMaximumPoolSize(config.getJdbcPoolSize());
                logger.info("HikariCP data source created and connected to the database");
            } catch (IOException e) {
                logger.error("Error creating HikariCP data source: " + e.getMessage());
                throw new IOException("Error creating HikariCP data source: " + e.getMessage());
            }
        }
        return dataSource;
    }

}
