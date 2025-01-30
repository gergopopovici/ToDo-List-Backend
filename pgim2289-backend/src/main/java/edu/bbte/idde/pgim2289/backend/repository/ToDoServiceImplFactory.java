package edu.bbte.idde.pgim2289.backend.repository;

import edu.bbte.idde.pgim2289.backend.services.ToDoServiceImplementation;


public class ToDoServiceImplFactory {
    public static ToDoServiceImplementation instance;
    public synchronized  static ToDoServiceImplementation getInstance(){
        if(instance == null){
            instance = new ToDoServiceImplementation();
        }
        return instance;
    }
}
