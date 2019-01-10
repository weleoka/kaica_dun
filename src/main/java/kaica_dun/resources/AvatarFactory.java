package kaica_dun.resources;

import kaica_dun.entities.Armor;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Weapon;
import kaica_dun_system.GameServiceImpl;
import kaica_dun_system.User;
import kaica_dun_system.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Class handling the creation of avatars for users.
 *
 * The avatar creation process could conceivably be quite complex and depending
 * on random generation, as well as multiple preferences supplied by user.
 *
 * There is also the added complexity of "stocking" a users inventory and
 * possibly applying options from, for example, "purchases" or "credits/points-system".
 *
 * todo: consider making this class singleton.
 *
 */
@Component
public class AvatarFactory {
    private User user;

    @Autowired
    UserServiceImpl usi;

    /**
     * Create a new avatar, belonging to a user.
     *
     * This class can be used to implement such things as Avatar guild/species/trade
     * an modify attributes of the avatar accordingly.
     */
    private AvatarFactory() {}



    /**
     * Pass user input for the creation of an avatar.
     *
     * @param name
     * @param description
     * @return
     */
    public Avatar make(String name, String description) {
        User user = usi.getAuthenticatedUser();

        return make(name, description, user);
    }


    /**
     * Pass user input and a user instance for the creation of an avatar.
     *
     * May or may not be a useful method. It depends if avatars should be possible to
     * create for users that are not logged in yet.
     * @param name
     * @param description
     * @param user
     * @return
     */
    public Avatar make(String name, String description, User user) {
        Weapon wep = createDefaultWeapon();
        Armor arm = createDefaultArmor();
        Avatar avatar = new Avatar(name, description, user, "User Avatar", 9000, 90, 1, 2, wep, arm);

        return avatar;
    }


    /**
     * Testing method that makes a quick and easy static avatar.
     *
     * todo: remove this method, move this out to a test framework and instead test true methods.
     *
     * @param user
     * @return
     */
    public Avatar makeTestAvatar(User user) {
        Weapon wep = createDefaultWeapon();
        Armor arm = createDefaultArmor();
        Avatar avatar = new Avatar("Billy the Burly", "Oh, yeah!", user, "User Avatar", 9000, 90, 1, 2, wep, arm);

        return avatar;
    }


    /**
     * Give a new avatar some sort of weapon, can be a random assignment from a
     * set of defaults, or derived from a purchase.
     *
     * @return
     */
    public Weapon createDefaultWeapon() {
        //Make Weapon
        return new Weapon("The Smashanizer", "Smashing!", 7, 4);
    }


    /**
     * Give a new avatar some sort of armor, can be a random assignment from a
     * set of defaults, or derived from a purchase.
     *
     * @return
     */
    public Armor createDefaultArmor() {
        //Make Armor
        return new Armor("Studded Leather", "A full suit of studded leather armor.", 2);

    }
}
