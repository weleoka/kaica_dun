package kaica_dun.resources;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.entities.Armor;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Item;
import kaica_dun.entities.Weapon;
import kaica_dun_system.User;
import org.springframework.beans.factory.annotation.Autowired;

public class makeAvatar {
    private User user;

    /**
     * Create a new avatar with static fields for testing, belonging to a user, that we can then save to the db.
     */
    private makeAvatar(){}

    public static Avatar make(User user) {

        //Make Weapon
        Weapon wep = new Weapon("The Smashanizer", "Smashing!", 4, 4);

        //Make Armor
        Armor arm = new Armor("Studded Leather", "A full suit of studded leather armor.", 2);

        //Make Avatar with the weapon and the armor equipped
        Avatar avatar = new Avatar("Billy the Burly", "Oh, yeah!", user, "User Avatar", 90, 90, 1, 2, wep, arm);

        return avatar;
    }
}
