package kaica_dun.dao;

import kaica_dun.entities.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvatarInterface extends JpaRepository<Avatar, UUID> {
}
