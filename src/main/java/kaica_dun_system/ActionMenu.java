package kaica_dun_system;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.in;
import static java.lang.System.out;

/**
 * Abstract class for in-game actions
 *
 * This menu is extended to give possiblities under the following
 *
 * 1. The player is manipulating the Avatar
 * 2. The player is manipulating the Room with his Avatar
 *
 * The "Menu"-hierarchy goes down to the actual game-cycle, but after that point the
 * "ActionMenu"-hierarchy takes over for displaying and accepting user choice in the game.
 *
 */
public abstract class ActionMenu {

    static final Scanner userInput = new Scanner(in);

    /**
     * Get the users input. Between iterations print the message output to user
     * Only values as integers specified in the validOptions will be accepted.
     *
     * @param validOptions
     * @param output
     * @return
     */
    public int getUserInput (Set<Integer> validOptions, String output) {
        int selection;

        inputLoop:
        while (true) {
            out.println(output);

            if (userInput.hasNextInt()) {

                selection = userInput.nextInt();

                if (validOptions.contains(selection)) {

                    break inputLoop;

                } else {
                    out.println(UI_strings.menuSelectionFailed);
                }
            }
            userInput.reset(); // flush the in buffer
        }

        return selection;
    }
}
