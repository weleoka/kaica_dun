package kaica_dun.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Not sure this will be used, but here for now while I think.
 */

public class DungeonFactory {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private EntityManager entityManager;


    public static void createDungeon() {


    }

}
