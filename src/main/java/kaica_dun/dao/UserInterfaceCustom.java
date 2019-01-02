package kaica_dun.dao;

import kaica_dun_system.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterfaceCustom {
    User findByName(String name);
}
