package kaica_dun.dao;


import kaica_dun_system.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInterface extends CrudRepository<User, Long>, UserInterfaceCustom {
}

