package kaica_dun_system.menus;

import kaica_dun.entities.Avatar;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import kaica_dun_system.ActionEngineServiceImpl;
import kaica_dun_system.UiString;
import kaica_dun_system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.out;

@Component
class MenuLoggedIn extends Menu {

    @Autowired
    private ActionEngineServiceImpl aesi;


    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    void display(User user) throws MenuException {
        int selection;

        Set<Integer> hset = new HashSet<>(Arrays.asList(1, 2, 9));
        selection = getUserInput(hset, UiString.loggedInMenu);

        switch (selection) {

            case 1: // Start a new game (or resume a game if the avatar is in a dungeon already.)
                Avatar avatar = selectAvatarPrompt(user);

                if (avatar != null) {

                    if (avatar.getCurrDungeon() != null) {
                        aesi.resume(avatar);

                    } else {
                        aesi.playNew(avatar);
                    }
                }
                break;

            case 2: // Create new Avatar
                createAvatar(user);
                break;

            case 9:
                out.println(UiString.logoutSuccessfull);
                Util.sleeper(700);
                throw new MenuException("Logged out");
        }
    }


    /**
     * A creating a new avatar to play as.
     *
     * arr[0] Name
     * arr[1] Description
     */
    private void createAvatar(User user) {
        String[] arr = this.createAvatarPrompt();

        if (gsi.createNewAvatar(arr, user)) {
            out.println(UiString.newAvatarCreated);
        }
    }




    // ********************** Prompts for user choice ********************** //


    /**
     * User input prompt for creating Avatar.
     * armor, currHealth, damage, description, maxHealth, name, type, equippedArmor, equippedWeapon, user
     *
     * arr[0] name
     * arr[1] description
     */
    private String[] createAvatarPrompt() {
        userInput.nextLine(); // flush the input buffer
        out.println(UiString.promptAvatarName);
        String name = userInput.nextLine(); // To accept whitespace.
        out.println(UiString.promptAvatarDescription);
        String description = userInput.nextLine();
        String[] tmpArr = {name, description};

        return tmpArr;
    }


    /**
     * A user can have many avatars and this lets them choose between them.
     *
     * @return
     */
    private Avatar selectAvatarPrompt(User user) {
        StringBuilder str = new StringBuilder();
        List<Avatar> avatars = gsi.fetchAvatarByUser(user);

        if (avatars.size() == 0) {
            out.println(UiString.noAvatarAvailable);
            return null;
        }
        str.append(UiString.selectYourAvatarHeader);

        for (int i = 0; i < avatars.size() ; i++) {
            str.append(String.format("\n[%s] - %s", i + 1, avatars.get(i).getName()));
        }
        str.append(UiString.makeSelectionPrompt);
        int sel = getUserInput(avatars.size(), str.toString());
        out.println(UiString.avatarSelectedSuccess);

        return avatars.get(sel - 1);
    }
}