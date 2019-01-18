package kaica_dun.dao;

import kaica_dun.entities.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemInterface extends CrudRepository<Item, UUID> {
}
