package kaica_dun.dao;

import kaica_dun.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomInterface extends CrudRepository<Room, UUID> {
}
