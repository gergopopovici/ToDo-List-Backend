package edu.bbte.idde.pgim2289.backend.repository;

import edu.bbte.idde.pgim2289.backend.repository.jdbc.JdbcDaoFactory;

public abstract class DaoFactory {
    // singleton lazy loading
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        /* if (instance == null) {
            instance = new MemDaoFactory();
        }*/
        if (instance == null) {
            instance = new JdbcDaoFactory();
        }
        return instance;
    }

    public abstract ToDoDao getToDoDao();

}
