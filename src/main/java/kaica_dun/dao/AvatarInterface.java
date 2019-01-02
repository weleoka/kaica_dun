package kaica_dun.dao;

import kaica_dun.entities.Avatar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarInterface extends CrudRepository<Avatar, Long> {
}
