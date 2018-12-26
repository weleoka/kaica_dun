package kaica_dun_system;

import kaica_dun.util.Util;

import static java.lang.System.out;

public class MenuMain extends Menu {

    /**
     * Menu default to all players.
     * <p>
     * Items:
     * 1) Login player
     * 2) Create player
     * -3) Generate default rooms
     * -4) empty
     * -5) List all users and their data.
     * 9) Quit Application
     * <p>
     * todo: test the inputLoop breaking and what happens to following switch cases.
     */

    static void display() {
        int selection = 0;

        inputLoop:
        while (true) {
            out.println(UI_strings.mainMenu);

            if (userInput.hasNextInt()) {
                selection = userInput.nextInt();

                switch (selection) {

                    case 1:
                        if (loginUser()) {
                            out.println(UI_strings.successfullLogin);
                            MenuLoggedIn.display();
                            break inputLoop;
                        } else {
                            out.println(UI_strings.unsuccessfullLogin);
                            continue;
                        }

                    case 2:
                        createUser();
                        break inputLoop;

                    case 3: // Secret case for making default rooms.
                        //generateDefaultRooms();
                        break inputLoop;

                    case 4: // Secret case for booking an activity.
                        //bookActivity();

                    case 5: // Secret case for listing users.
                        listUsers();

                    case 9:
                        quit();
                        break inputLoop;
                }
                userInput.reset(); // flush the in buffer

            } else {
                out.println(UI_strings.menuSelectionFailed);
            }
        }
    }



    // - - - Prompts for user input - - -

    /**
     * Simple name and password prompt
     *
     * @returns tmpArr             an array of name and password pair
     */
    private static String[] credentialsPrompt() {
        userInput.nextLine(); // flush the input buffer
        out.println(UI_strings.promptUserName);
        String userName = userInput.nextLine(); // To accept whitespace.
        out.println(UI_strings.promptUserID);
        String userID = userInput.nextLine();
        String[] tmpArr = {userName, userID};

        return tmpArr;
    }


    // - - - Functionality - - -

    /**
     * Steps for logging user in.
     *
     * @return boolean
     */
    private static boolean loginUser() {
        String[] creds = credentialsPrompt();

        if (USERCONTROL.selectUserByName(creds[0])) // Find user in the db.
        {
            out.println(UI_strings.userNameFound);

            if (true) // Test validity of supplied ID.
            {
                out.println(UI_strings.userIDValid);

                if (USERCONTROL.loginSelectedUser(creds[1])) // Test ID and name pair for match in db.
                {

                    return true;

                } else {
                    out.println(UI_strings.userNameToIDMissmatch);
                    Util.sleeper(700);

                    return false;
                }

            } else {
                out.println(UI_strings.userIDInvalid);
                Util.sleeper(700);

                return false;
            }

        } else {
            out.println(UI_strings.userNameNotFound);
            Util.sleeper(700);

            return false;
        }

    }



    // - - - New object creation processes - - -

    /**
     * Registration process for a new user.
     */
    private static void createUser() {
        out.println(UI_strings.createUserHeader);
        String[] creds = credentialsPrompt();

        if (true) {

            if (USERCONTROL.createUser(creds[0], creds[1])) {
                out.println(UI_strings.createUserSuccess);
            } else {
                out.println(UI_strings.createUserFail);
            }
        } else {
            out.println(UI_strings.userIDInvalid);
        }
        Util.sleeper(700);
        //createSubscription();
        MenuLoggedIn.display();
    }


    /**
     * List all the users in the database.
     */
    private static void listUsers() {
        //USERCONTROL.printUserList();
        MenuMain.display();
    }

    /**
     * End the application.
     */
    private static void quit() {
        out.println(UI_strings.goodbyeString);
        System.exit(0);
    }

}