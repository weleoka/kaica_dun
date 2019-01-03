package kaica_dun.dao;

import kaica_dun_system.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInterfaceCustom {
    List<User> findByName(String name);
}
