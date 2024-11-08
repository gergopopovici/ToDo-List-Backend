package edu.bbte.idde.pgim2289.backend.repository.mem;

import edu.bbte.idde.pgim2289.backend.repository.DaoFactory;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;

public class MemDaoFactory extends DaoFactory {

    @Override
    public ToDoDao getToDoDao() {
        return new ToDoMemDao();
    }
}
