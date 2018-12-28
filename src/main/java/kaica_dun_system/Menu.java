package kaica_dun_system;

import java.util.Scanner;

import static java.lang.System.in;

abstract class Menu {
    static final UserControl USERCONTROL = UserControl.getInstance();
    static final GameControl GAMECONTROL = GameControl.getInstance();
    static final Scanner userInput = new Scanner(in);
}


