package kaica_dun.dao;


import java.io.Serializable;
import java.util.List;

public interface DaoInterface<T, PK extends Serializable> {
    T create(T t);
    T read(PK id);
    T update(T t);
    void delete(T t);
    public List<T> findAll();
}

