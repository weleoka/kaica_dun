package kaica_dun.dao;

import kaica_dun.entities.Container;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContainerInterface extends JpaRepository<Container, UUID> {
}
