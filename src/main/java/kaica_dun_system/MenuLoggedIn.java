package kaica_dun_system;

import kaica_dun.util.Util;

import static java.lang.System.out;
import static kaica_dun_system.UI_strings.mainMenu;

public class MenuLoggedIn extends Menu {



    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    static void display() {
        int selection;

        if (!USERCONTROL.isAuthenticatedUser()) {
            out.println(UI_strings.userNotAuthenticated);
            MenuMain.display();
        }

        inputLoop:
        while (true) {
            out.println(UI_strings.loggedInMenu);

            if (userInput.hasNextInt()) {
                selection = userInput.nextInt();

                switch (selection) {

                    case 1:

                        continue;

                    case 2:

                        continue;

                    case 9:
                        logoutUser();
                        break inputLoop;
                }
            } else {
                System.out.println(UI_strings.menuSelectionFailed);
            }
            userInput.reset(); // flush the in buffer
        }
    }


    /**
     * Steps for logging user out.
     */
    private static void logoutUser() {
        USERCONTROL.logoutSelectedUser();
        out.println(UI_strings.logoutSuccessfull);
        Util.sleeper(700);
        MenuMain.display();
    }

}
