package kaica_dun_system;

import kaica_dun.util.MenuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * The menu for entering and leaving a game.
 *
 * See abstract superclass for details.
 */
@Component
public class MenuInGame extends Menu {

    @Autowired
    MenuLoggedIn menuLoggedIn;

    @Autowired
    ActionEngineServiceImpl aesi;

    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Resume previous game
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    public void display(boolean directPlay) throws MenuException { // todo: change to private after testing.
        int selection;

        if (directPlay) {
            aesi.playNew(); // Jump straight in the game.
        }

        Set<Integer> hset = new HashSet<>(Arrays.asList(1, 2, 3 ,9));
        selection = getUserInput(hset, UiString.inGameMenu);

        switch (selection) {

            case 1: // Resume playing the game
                aesi.resume();
                break;

            case 2: // Restart the same dungeon
                aesi.restart();
                break;

            case 3: // Quits with saving
                //closeGameActions();
                break;

            case 9: // Quits no saving.
                //closeGameActions();
                break;
        }

        // Break out to the top menu.
        throw new MenuException("Quit the current game"); //menuLoggedIn.display();
    }
}
