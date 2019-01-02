package kaica_dun.dao;

import kaica_dun.entities.Dungeon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DungeonInterface extends CrudRepository<Dungeon, Long> {
}
