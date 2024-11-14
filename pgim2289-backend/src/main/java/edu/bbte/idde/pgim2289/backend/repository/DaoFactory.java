package edu.bbte.idde.pgim2289.backend.repository;

import edu.bbte.idde.pgim2289.backend.config.Config;
import edu.bbte.idde.pgim2289.backend.config.ConfigLoader;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.repository.jdbc.JdbcDaoFactory;
import edu.bbte.idde.pgim2289.backend.repository.mem.MemDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class DaoFactory {
    // singleton lazy loading
    private static DaoFactory instance;
    private static final Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            try {
                String profile = System.getenv().getOrDefault("APP_PROFILE", "jdbc");
                Config config = ConfigLoader.loadConfig(profile);
                if ("inMemory".equalsIgnoreCase(config.getDaoType())) {
                    instance = new MemDaoFactory();
                    logger.info("Creating MemDaoFactory");
                } else if ("jdbc".equalsIgnoreCase(config.getDaoType())) {
                    instance = new JdbcDaoFactory();
                    logger.info("Creating JdbcDaoFactory");
                } else {
                    logger.error("Unknown DAO type: " + config.getDaoType());
                    throw new InvalidInputException("Unknown DAO type: " + config.getDaoType());
                }
            } catch (IOException e) {
                logger.error("Error creating DAO factory: " + e.getMessage());
                throw new InvalidInputException("Error creating DAO factory: " + e.getMessage());
            }
        }
        return instance;
    }

    public abstract ToDoDao getToDoDao();

}
