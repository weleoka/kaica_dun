package kaica_dun.dao;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Item;
import kaica_dun_system.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInterface extends CrudRepository<Item, Long> {
}
