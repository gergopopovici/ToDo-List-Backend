package edu.bbte.idde.pgim2289.backend.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataSourceFactory {
    private static HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new HikariDataSource();
            logger.info("Creating HikariCP data source and connecting to the database");
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/toDo");
            dataSource.setUsername("root");
            dataSource.setPassword("admin");
            dataSource.setMaximumPoolSize(15);
            logger.info("HikariCP data source created and connected to the database");
        }
        return dataSource;
    }

}
