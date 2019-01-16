package kaica_dun_system.menus;

import kaica_dun_system.GameServiceImpl;
import kaica_dun_system.UiString;
import kaica_dun_system.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Abstract class for main menu actions
 *
 * 1. The menu for a non-logged in user
 * 2. The menu for a logged in user
 * 3. The menu for leaving/saving/restarting a game
 *
 * The "Menu"-hierarchy goes down to the actual game-cycle, but after that point the
 * "ActionMenu"-hierarchy takes over for displaying and accepting user choice in the game.
 */
public abstract class Menu {
    public static final Logger log = LogManager.getLogger();

    @Autowired
    public UserServiceImpl usi;

    @Autowired
    public GameServiceImpl gsi;// = GameServiceImpl.getInstance();
    static final Scanner userInput = new Scanner(in);


    /**
     * Get users input but passing in the size of a list to generate the selection
     * possibilities for. Pass the resulting set of valid choice to next method.
     *
     * todo: if there is time check out if there is a cooler way of doing this.
     *
     * @param validOptions an int that usualy represents the size of the relevant list
     * @param output a string to display to user to aid in choice
     * @return
     */
    public int getUserInput (int validOptions, String output) {
        Integer[] options = new Integer[validOptions]; // Create fixed length array.

        for (int i = 0; i < validOptions; i++) {
            options[i] = i + 1;
        }
        Set<Integer> hset = new HashSet<>(Arrays.asList(options));

        return getUserInput(hset, output);
    }


    /**
     * Get the users input. Between iterations print the message output to user
     * Only values as integers specified in the validOptions will be accepted.
     *
     * Incrementing and decrementing for selection should be done external to this method.
     *
     * @param validOptions a set of integers that represent accepted values
     * @param output a string to display to user to aid in choice
     * @return selection an in that results from user choice.
     */
    public int getUserInput(Set<Integer> validOptions, String output) {
        int selection;

        inputLoop:
        while (true) {
            out.println(output);

            if (userInput.hasNextInt()) {

                selection = userInput.nextInt();

                if (validOptions.contains(selection)) {

                    break inputLoop;

                } else {
                    out.println(UiString.menuSelectionFailed);
                }
            }
            userInput.reset(); // flush the in buffer
        }

        return selection;
    }
}

