package kaica_dun.dao;

import kaica_dun.entities.Avatar;
import kaica_dun_system.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarInterface extends CrudRepository<Avatar, Long> {
}
