package kaica_dun_system;

import kaica_dun.entities.Avatar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class GameControl {
    private static GameControl ourInstance = new GameControl();

    public static GameControl getInstance() {
        return ourInstance;
    }

    private GameControl() {}

    private static final Logger log = LogManager.getLogger();
    private Session session = SessionUtil.getSession();


    public List<Avatar> fetchAvatarByUser(User user) {
        log.debug("Searching for all Avatars belonging to {}.", user.getName());

        Query query = this.session.createSQLQuery(
                "SELECT * FROM avatar a WHERE a.userID LIKE :userID")
                .addEntity(Avatar.class)
                .setParameter("userID", user.getId());
        List<Avatar> result = query.getResultList(); // List<User> result = query.getResultList();

        log.debug(result);

        return result;
    }

    public void createNewAvatar(User user) {
        Avatar avatar = new Avatar(user, "LoggenInMenuAvatarNoWeapon", "Run!", "User Avatar", 90, 90, 1, 2);

        session.save(avatar);
    }
}
