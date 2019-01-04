package kaica_dun_system;

import kaica_dun.util.KaicaException;
import kaica_dun.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

@Component
public class MenuMain extends Menu {

    @Autowired
    private MenuLoggedIn menuLoggedIn;

    public MenuMain () {}
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
    public void display() throws KaicaException {
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
                            menuLoggedIn.display();
                            continue;

                        } else {
                            out.println(UI_strings.unsuccessfullLogin);
                            continue;
                        }

                    case 2: // Create a new user.
                        createUser();
                        continue;
                        //break inputLoop;

                    case 5: // Secret case for listing users.
                        usi.printUserList();
                        continue;

                    case 9:
                        quit();
                        break inputLoop;
                }

            } else {
                out.println(UI_strings.menuSelectionFailed);
            }
            userInput.reset(); // flush the in buffer
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
    private boolean loginUser() {
        String[] creds = credentialsPrompt();
        User user = usi.findUserByName(creds[0]);

        if (user != null) {
            out.println(UI_strings.userNameFound);

            if (usi.loginUser(user, creds[1])) {

                return true;

            } else {
                out.println(UI_strings.userNameToPasswordMismatch); // todo: change to userNameToPwdMismatch
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
     *
     * userArr[0] is the userName
     * userArr[1] is the password
     *
     */
    private void createUser() {
        out.println(UI_strings.createUserHeader);
        String[] creds = credentialsPrompt();
        User user = new User(creds[0], creds[1]);
        log.debug("Creating user: {} with password: {}", creds[0], creds[1]);

        if (usi.checkNewUserName(creds[0])) {

            if (usi.createUser(user) != null) {
                out.println(UI_strings.createUserSuccess);
                Util.sleeper(700);

            } else {
                out.println(UI_strings.createUserFail);
            }

        } else {
            out.println(UI_strings.userNameExists);
        }
        Util.sleeper(700);
    }


    /**
     * End the application.
     */
    private void quit() throws KaicaException {
        out.println(UI_strings.goodbyeString);
        throw new KaicaException("Quit application");
        //System.exit(0);
        // Returns to caller
    }

}