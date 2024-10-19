package edu.bbte.idde.pgim2289.repository.mem;

import edu.bbte.idde.pgim2289.repository.DaoFactory;
import edu.bbte.idde.pgim2289.repository.ToDoDao;

public class MemDaoFactory extends DaoFactory {

    @Override
    public ToDoDao getToDoDao() {
        return new ToDoMemDao();
    }
}
