package kaica_dun_system;

import kaica_dun.util.KaicaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.out;


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
    public void display(boolean directPlay) throws KaicaException { // todo: change to private after testing.
        int selection;

        if (directPlay) {
            aesi.play();// Jump straight in the game.
        }

        inputLoop:
        while (true) {
            out.println(UI_strings.inGameMenu);

            if (userInput.hasNextInt()) {
                selection = userInput.nextInt();

                switch (selection) {

                    case 1: // Resume playing the game
                        aesi.resume();
                        continue;

                    case 2: // Restart the same dungeon
                        aesi.restart();
                        continue;

                    case 3: // Quits with saving
                        //closeGameActions();
                        break inputLoop;

                    case 9: // Quits no saving.
                        //closeGameActions();
                        break inputLoop;
                }

            } else {
                out.println(UI_strings.menuSelectionFailed);
            }
            userInput.reset(); // flush the in buffer
        }
        // Break out to the top menu.
        throw new KaicaException("Quit the game"); //menuLoggedIn.display();
    }
}
