package edu.bbte.idde.pgim2289.repository.jdbc;

import edu.bbte.idde.pgim2289.repository.DaoFactory;
import edu.bbte.idde.pgim2289.repository.ToDoDao;

public class JdbcDaoFactory extends DaoFactory {
    private ToDoDao toDoDao;

    @Override
    public ToDoDao getToDoDao() {
        if (toDoDao == null) {
            toDoDao = new ToDoJdbcDao();
        }
        return toDoDao;
    }
}
