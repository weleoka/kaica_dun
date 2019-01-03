package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.util.Util;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.System.out;

@Component
public class MenuLoggedIn extends Menu {

    MenuLoggedIn() {}

    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    void display() {
        int selection;

        if (!usi.isAuthenticatedUser()) {
            out.println(UI_strings.userNotAuthenticated);
            returnToMainMenu();
        }

        inputLoop:
        while (true) {
            out.println(UI_strings.loggedInMenu);

            if (userInput.hasNextInt()) {
                selection = userInput.nextInt();

                switch (selection) {

                    case 1: // Start a new Dungeon game
                        selectAvatar();
                        continue;

                    case 2: // Create new Avatar
                        createAvatar();
                        continue;

                    case 9:
                        this.logoutUser();
                        break inputLoop;
                }
            } else {
                out.println(UI_strings.menuSelectionFailed);
            }
            userInput.reset(); // flush the in buffer
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
            out.println(UI_strings.newAvatarCreated);
        }

        this.display(); // Return to Logged in menu prompt.
    }

    /**
     * A selecting an existing Avatar to play as.
     */
    private void selectAvatar() {
        Avatar tmpAvatar = selectAvatarPrompt();

        if (tmpAvatar != null) {
            gsi.setAvatar(tmpAvatar);
        }
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
        out.println(UI_strings.promptAvatarName);
        String name = userInput.nextLine(); // To accept whitespace.
        out.println(UI_strings.promptAvatarDescription);
        String description = userInput.nextLine();
        String[] tmpArr = {name, description};

        return tmpArr;
    }


    private Avatar selectAvatarPrompt() {
        int selection;
        String selectionOptions = "";
        List<Avatar> avatarList = gsi.fetchAvatarByUser(usi.getAuthenticatedUser());

        if (avatarList.size() == 0) {
            out.println(UI_strings.noAvatarAvailable);

            return null;
        }

        for (int i = 0; i < avatarList.size(); i++) {
            Avatar tmpAvatar = avatarList.get(i);
            //out.println(tmpAvatar.toString());
            selectionOptions += String.format("[%s] - %s", i + 1, tmpAvatar.getName()); // plus 1 for readability
        }

        inputLoop:
        while (true) {
            out.println(UI_strings.selectYourAvatarHeader);
            out.println(selectionOptions);
            out.println(UI_strings.makeSelectionPrompt);

            if (userInput.hasNextInt()) {
                selection = userInput.nextInt() - 1;    // Minus 1 for correct index.

                if (selection > 0 && selection < avatarList.size()) {

                    return avatarList.get(selection);

                } else {
                    out.println(UI_strings.menuSelectionFailed);
                }
                userInput.reset(); // flush the in buffer
            }
        }
    }





    /**
     * Return to Main Menu.
     */
    private void returnToMainMenu() {
        MenuMain menuMain = new MenuMain();
        menuMain.display();
    }


    /**
     * Steps for logging user out.
     */
    private void logoutUser() {
        usi.logoutUser();
        out.println(UI_strings.logoutSuccessfull);
        Util.sleeper(700);
        returnToMainMenu();
    }
}
