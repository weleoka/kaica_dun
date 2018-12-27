package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.util.Util;

import java.util.List;

import static java.lang.System.out;


public class MenuLoggedIn extends Menu {

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

        if (!USERCONTROL.isAuthenticatedUser()) {
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
     */
    private void createAvatar() {
        this.createAvatarPrompt();
        GAMECONTROL.createNewAvatar(USERCONTROL.getSelectedUser());
        this.display(); // Return to Logged in menu prompt.
    }

    /**
     * A selecting an existing Avatar to play as.
     */
    private void selectAvatar() {
        this.selectAvatarPrompt();

    }




    // Prompts for user choice
    private void createAvatarPrompt() {

    }
    private Avatar selectAvatarPrompt() {
        List<Avatar> avatarList = GAMECONTROL.fetchAvatarByUser(USERCONTROL.getSelectedUser());

        for (int i = 0; i < avatarList.size(); i++) {
            Avatar tmp_avatar = avatarList.get(i);
            out.println(tmp_avatar.toString());
        }



        Avatar avatar = avatarList.get(0);
        return avatar;
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
        USERCONTROL.logoutSelectedUser();
        out.println(UI_strings.logoutSuccessfull);
        Util.sleeper(700);
        returnToMainMenu();
    }
}
