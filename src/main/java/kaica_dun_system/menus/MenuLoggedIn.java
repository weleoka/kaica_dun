package kaica_dun_system.menus;

import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import kaica_dun_system.ActionEngineServiceImpl;
import kaica_dun_system.UiString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.System.out;

@Component
public class MenuLoggedIn extends Menu {

    @Autowired
    MenuMain menuMain;

    @Autowired
    MenuInGame menuInGame;

    @Autowired
    ActionEngineServiceImpl aesi;

    @Autowired // Can be removed in development.
            UserInterface userInterface;


    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    void display() throws MenuException {
        int selection;

        if (!usi.isAuthenticatedUser()) {
            out.println(UiString.userNotAuthenticated);
            Util.sleeper(900);
            log.warn("REMEBER to disable access to MenuLoggedIn#display for production.");
            //return;
        }

        Set<Integer> hset = new HashSet<>(Arrays.asList(1, 2, 9));
        selection = getUserInput(hset, UiString.loggedInMenu);

        switch (selection) {

            case 1: // Start a new game (or resume a game if the avatar is in a dungeon already.)

                if (selectAvatar()) {
                    if (gsi.getAvatarCurrentRoom() != null) {
                        aesi.resume();
                    } else {
                        aesi.playNew();
                    }
                }
                break;

            case 2: // Create new Avatar
                createAvatar();
                break;

            case 9:
                this.logoutUser();
                throw new MenuException("Logged out");
        }
    }


    /**
     * A creating a new avatar to play as.
     *
     * arr[0] Name
     * arr[1] Description
     */
    private void createAvatar() {
        String[] arr = this.createAvatarPrompt();
        if (gsi.createNewAvatar(arr, usi.getAuthenticatedUser())) {
            out.println(UiString.newAvatarCreated);
        }
    }


    /**
     * A selecting an existing Avatar to play as.
     */
    private boolean selectAvatar() {
        Avatar tmpAvatar = selectAvatarPrompt();

        if (tmpAvatar != null) {
            gsi.setAvatar(tmpAvatar);

            return true;
        }

        return false;
    }


    // Prompts for user choice

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
    private Avatar selectAvatarPrompt() {
        int selection;
        String selectionOptions = "";
        List<Avatar> avatarList = gsi.fetchAvatarByUser(usi.getAuthenticatedUser());

        if (avatarList.size() == 0) {
            out.println(UiString.noAvatarAvailable);

            return null;
        }

        selectionOptions = gsi.printAvatarListByUser(usi.getAuthenticatedUser(), false); // print to stdout.


        inputLoop:
        while (true) {
            out.println(UiString.selectYourAvatarHeader);
            out.println(selectionOptions);
            out.println(UiString.makeSelectionPrompt);

            if (userInput.hasNextInt()) {
                selection = userInput.nextInt() - 1;

                if (selection >= 0 && selection < avatarList.size()) {
                    Avatar avatar = avatarList.get(selection);
                    out.println(UiString.avatarSelectedSuccess + avatar.getName());

                    return avatarList.get(selection);

                } else {
                    out.println(UiString.menuSelectionFailed);
                }
                userInput.reset(); // flush the in buffer
            }
        }
    }



    /**
     * Steps for logging user out.
     */
    private void logoutUser() {
        usi.logoutUser();
        out.println(UiString.logoutSuccessfull);
        Util.sleeper(700);
    }

}