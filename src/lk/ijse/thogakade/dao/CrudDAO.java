package lk.ijse.thogakade.dao;

import java.util.ArrayList;

public interface CrudDAO <T,ID> extends  SuperDAO{
    boolean add(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;
    T search(ID id) throws Exception;
    ArrayList<T> getAll() throws Exception;
}
