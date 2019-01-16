package kaica_dun_system.menus;

import kaica_dun.util.MenuException;
import kaica_dun.util.QuitException;
import kaica_dun.util.Util;
import kaica_dun_system.UiString;
import kaica_dun_system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
     * 1) Login user
     * 2) Create user
     * -5) List all users and their data.
     * 9) Quit Application
     * <p>
     */
    public void display() throws QuitException {
        int selection = 0;

        Set<Integer> hset = new HashSet<>(Arrays.asList(1, 2, 5 ,9));
        selection = getUserInput(hset, UiString.mainMenu);

        switch (selection) {

            case 1:
                User user = loginUser();
                if (user != null) {
                    out.println(UiString.successfullLogin);
                    displayLoggedInMenu(user);
                    break;

                } else {
                    out.println(UiString.unsuccessfullLogin);
                    break;
                }

            case 2: // Create a new user.
                createUser();
                break;

            case 5: // Secret case for listing users.
                usi.printUserList();
                break;

            case 9:
                throw new QuitException("Quit application");

        }
    }


    /**
     * Displays the logged in menu in a loop that
     * is stopped if an exception is thrown.
     */
    private void displayLoggedInMenu(User user) {

        while(true) {

            try {
                menuLoggedIn.display(user);

            } catch (MenuException e) {
                break;
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
        out.println(UiString.promptUserName);
        String userName = userInput.nextLine(); // To accept whitespace.
        out.println(UiString.promptUserID);
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
    private User loginUser() {
        String[] creds = credentialsPrompt();
        User user = usi.findUserByName(creds[0]);

        if (user != null) {
            out.println(UiString.userNameFound);

            if (!usi.loginUser(user, creds[1])) {
                out.println(UiString.userNameToPasswordMismatch);
                Util.sleeper(700);
                user = null;
            }

        } else {
            out.println(UiString.userNameNotFound);
            Util.sleeper(700);
        }

        return user;

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
        out.println(UiString.createUserHeader);
        String[] creds = credentialsPrompt();
        User user = new User(creds[0], creds[1]);
        log.debug("Creating user: {} with password: {}", creds[0], creds[1]);

        if (usi.checkNewUserName(creds[0])) {

            if (usi.createUser(user) != null) {
                out.println(UiString.createUserSuccess);
                Util.sleeper(700);

            } else {
                out.println(UiString.createUserFail);
            }

        } else {
            out.println(UiString.userNameExists);
        }
        Util.sleeper(700);
    }


    /**
     * End the application.
     */
    private void quit() throws QuitException {

        //System.exit(0);
        // Returns to caller
    }

}