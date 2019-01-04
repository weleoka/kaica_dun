package kaica_dun_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.System.out;

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
    void display(boolean directPlay) {
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

                    case 1: // Resume the game
                        aesi.resume();
                        continue;

                    case 2:
                        aesi.restart();
                        continue;

                    case 3:
                        //saveAndQuit();
                        menuLoggedIn.display();
                        break inputLoop;

                    case 9:
                        // closeGameActions();
                        //menuLoggedIn.display(); // Quits no saving.
                        break inputLoop;
                }

            } else {
                out.println(UI_strings.menuSelectionFailed);
            }
            userInput.reset(); // flush the in buffer
        }
    }
}
