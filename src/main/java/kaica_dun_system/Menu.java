package kaica_dun_system;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

import static java.lang.System.in;

abstract class Menu {
    public static final Logger log = LogManager.getLogger();

    @Autowired
    public UserServiceImpl usi;

    @Autowired
    public GameServiceImpl gsi;// = GameServiceImpl.getInstance();
    static final Scanner userInput = new Scanner(in);
}


