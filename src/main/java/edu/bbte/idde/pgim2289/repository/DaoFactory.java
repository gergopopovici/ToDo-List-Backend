package edu.bbte.idde.pgim2289.repository;

import edu.bbte.idde.pgim2289.repository.mem.MemDaoFactory;

public abstract class DaoFactory {
    // singleton lazy loading
    private static DaoFactory instance;
    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new MemDaoFactory();
        }
        return instance;
    }
    public abstract ToDoDao getToDoDao();

}
