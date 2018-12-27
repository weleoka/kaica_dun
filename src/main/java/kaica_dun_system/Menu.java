package kaica_dun_system;

import org.hibernate.Session;

import java.util.Scanner;

import static java.lang.System.in;

abstract class Menu {
    static final Scanner userInput = new Scanner(in);
    static final UserControl USERCONTROL = UserControl.getInstance();
    static final GameControl GAMECONTROL = GameControl.getInstance();
}


