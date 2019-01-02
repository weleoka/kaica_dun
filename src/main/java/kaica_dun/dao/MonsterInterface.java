package kaica_dun.dao;

import kaica_dun.entities.Monster;
import kaica_dun_system.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterInterface extends CrudRepository<Monster, Long> {
}

