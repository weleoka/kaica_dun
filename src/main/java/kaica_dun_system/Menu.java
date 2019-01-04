package kaica_dun_system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

import static java.lang.System.in;

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
abstract class Menu {
    public static final Logger log = LogManager.getLogger();

    @Autowired
    public UserServiceImpl usi;

    @Autowired
    public GameServiceImpl gsi;// = GameServiceImpl.getInstance();
    static final Scanner userInput = new Scanner(in);
}


