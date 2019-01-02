package kaica_dun.dao;


import kaica_dun_system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserInterface extends JpaRepository<User, Long> {

    // This could extend CrudRepository but keeping the implementation more basic.
 /*   T create(T t);
    T read(ID id);
    T update(T t);
    void delete(T t);
    public List<T> findAll();*/
}

