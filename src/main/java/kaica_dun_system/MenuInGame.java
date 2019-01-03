package kaica_dun_system;

import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.System.out;

public class MenuInGame extends Menu {

    @Autowired
    MenuLoggedIn menuLoggedIn;

    @Autowired
    ActionEngineServiceImpl aesi;

    /**
     * Menu displayed to a user when it is authenticated.
     * <p>
     * Items:
     * 1. Book Activity
     * (2. Change Subscription)
     * 9. Return to Main Menu
     */
    void display(boolean state) {
        int selection;

        if (state) {
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
                        menuLoggedIn.display();
                        //saveAndQuit();
                        continue;

                    case 9:
                        menuLoggedIn.display(); // Quits no saving.
                        break inputLoop;
                }

            } else {
                out.println(UI_strings.menuSelectionFailed);
            }
            userInput.reset(); // flush the in buffer
        }
    }
}
