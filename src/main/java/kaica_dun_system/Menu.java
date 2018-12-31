package kaica_dun_system;

import java.util.Scanner;

import static java.lang.System.in;

abstract class Menu {
    static final UserServiceImpl USERCONTROL = new UserServiceImpl();
    static final GameControl GAMECONTROL = GameControl.getInstance();
    static final Scanner userInput = new Scanner(in);
}


