package kaica_dun.dao;

import kaica_dun.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInterface extends CrudRepository<Room, Long> {
}
