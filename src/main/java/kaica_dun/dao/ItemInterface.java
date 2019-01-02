package kaica_dun.dao;

import kaica_dun.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInterface extends CrudRepository<Item, Long> {
}
