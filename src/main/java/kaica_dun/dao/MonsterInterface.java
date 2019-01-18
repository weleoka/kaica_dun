package kaica_dun.dao;

import kaica_dun.entities.Monster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MonsterInterface extends CrudRepository<Monster, UUID> {

    void removeMonsterById(Long id);
}

