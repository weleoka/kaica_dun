package kaica_dun.dao;

import kaica_dun.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInterface extends JpaRepository<Room, Long> {
}
