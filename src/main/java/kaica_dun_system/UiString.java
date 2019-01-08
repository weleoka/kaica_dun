package kaica_dun_system;

import kaica_dun.util.Util;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.in;

/**
 * Language specific strings for usage in Kaica_dungeon
 *
 * This is a static class which will not maintain state of a game but contains certain methods for
 * outputing strings to the user, or processing strings and then returning to caller.
 *
 */
public class UiString
{
    public static Random rand = new Random();
    private static final Scanner userInput = new Scanner(in);


    // Menus and selection
    public static String makeSelectionPrompt = "\n\nMake a selection:";
    public static String menuSelectionFailed = "Invalid menu choice.";
    public static String goodbyeString = "\n\nGood bye!.";

    // Main menu
    public static String menuHeader = "\n - - - Main Menu - - -";
    public static String mainMenuAlternatives = "\n1. Log in\n2. Register user\n9. Quit";
    public static String mainMenu = menuHeader + mainMenuAlternatives + makeSelectionPrompt;

    // Logged in menu
    public static String menuHeader2 = "\n - - - User Logged in Menu - - -";
    public static String loggedInMenuAlternatives = "\n1. Select Avatar\n2. Create Avatar\n9. Log out";
    public static String loggedInMenu = menuHeader2 + loggedInMenuAlternatives + makeSelectionPrompt;

    // In-game menu
    public static String menuHeader3 = "\n - - - Game Menu - - -";
    public static String inGameMenuAlternatives = "\n1. Resume\n2. Restart\n3. Save and Quit\n9. Quit Game";
    public static String inGameMenu = menuHeader3 + inGameMenuAlternatives + makeSelectionPrompt;


    // ACTION menu
    public static String menuHeader4 = "\n - - - Game Actions - - -";
    public static String lookAtMenuHeader = "\n - - - Look at something - - -";
    public static String moveMenuHeader = "\n - - - Time to move to the next room - - -";
    public static String battleMenuHeader = "\n - - - Fight the monsters - - -";



    // Dungeon navigation
    public static String welcomeToTheDungeon1 = "\n ... (darkness) ....";
    public static String welcomeToTheDungeon2 =
            "\n your eyes slowly adjust to the dim light shed by a smoking torch mounted on the wall.";
    public static String welcomeToTheDungeon3 = "" +
            "\n In the back of your mind there's a nagging feeling of doubt... maybe this wasn't such a good idea..." +
            "\n maybe you should've saved a bit more money and got that chain-mail armor before jumping down into this" +
            "\n dark and damp place. " +
            "\n\n You notice a smell in the damp air too... it reminds you of burnt hair... or feathers, or is it burnt skin?" +
            "\n It makes you wonder what poor hairy creature has been fried to cinders down here in the dungeon." +
            "\n\n But who would be toasting rats over a fire down here? This place was not supposed to have anything " +
            "\n living in it according to the locals... only rats, and maybe the odd old wrinkly little beggar of a greenskin." +
            "\n\n Hang on! Over there in the corner, about 50 meters away something just moved. Actually listening" +
            "\n intently you can hear strange sounds and whispers of old echoes! " +
            "\n\n Indeed you quickly come to realise that you are not alone in this place!" +
            "\n\n Best to get moving right now, and find what we came down here for as quick as possible! Good luck!";



    // Dungeon Randomness
    public static String randomSound1 = "\n- drip, drip, drip - there's an leaking old copper pipe.";
    public static String randomSound2 = "\n- thump, thump, thump - strange sounds come echoing down the passage.";
    public static String randomSound3 = "\n- wheeze, cough, cough - somebody is sleeping wrapped up in old furs in the corner.";


    public static String randomVisual1 = "\n... a spider runs across your feet.";
    public static String randomVisual2 = "\n... there's a twinkle of light reflecting of a puddle on the floor.";
    public static String randomVisual3 = "\n... something looking like dragon scales litter the floor.";








    // Avatar creation
    public static String promptAvatarName = "Enter a name for your Avatar: ";
    public static String promptAvatarDescription = "Enter a description of your Avatar: ";
    public static String newAvatarCreated = "New avatar has been created!";

    // Avatar selection
    public static String selectYourAvatarHeader = "\n - - - Avatar List - - -";
    public static String noAvatarAvailable = "You don't have any Avatars to play as. Create a new one.";
    public static String avatarSelectedSuccess = "You have selected to play as: ";




    // User creation
    public static String createUserHeader = "--> Create new user";
    public static String promptUserName = "Enter a username:";
    public static String promptUserID = "Enter a password:";
    public static String userNameExists = "The username exists.";
    public static String createUserSuccess = "Your user has been created.";
    public static String createUserFail = "Something went wrong, please try again.";

    // User authorisation
    public static String userNotAuthenticated = "Not logged in.";
    public static String userNameFound = "The user was found in the database.";
    public static String userNameNotFound = "The user was not found in the database.";
    public static String userNameToPasswordMismatch = "The username and password combination ar incorrect.";
    public static String successfullLogin = "Log-in successful.";
    public static String unsuccessfullLogin = "Log-in failed. Try again.";
    public static String logoutSuccessfull = "You are now logged out.";




    public static String logo =
"db   dD  .d8b.  d888888b  .o88b.  .d8b.        d8888b. db    db d8b   db  d888b  d88888b  .d88b.  d8b   db\n" +
"88 ,8P' d8' `8b   `88'   d8P  Y8 d8' `8b       88  `8D 88    88 888o  88 88' Y8b 88'     .8P  Y8. 888o  88\n" +
"88,8P   88ooo88    88    8P      88ooo88       88   88 88    88 88V8o 88 88      88ooooo 88    88 88V8o 88\n" +
"88`8b   88~~~88    88    8b      88~~~88       88   88 88    88 88 V8o88 88  ooo 88~~~~~ 88    88 88 V8o88\n" +
"88 `88. 88   88   .88.   Y8b  d8 88   88       88  .8D 88b  d88 88  V888 88. ~8~ 88.     `8b  d8' 88  V888\n" +
"YP   YD YP   YP Y888888P  `Y88P' YP   YP       Y8888D' ~Y8888P' VP   V8P  Y888P  Y88888P  `Y88P'  VP   V8P\n";



    public static void printGameIntro() {
        System.out.println(UiString.welcomeToTheDungeon1);
        Util.sleeper(1800);
        System.out.println(UiString.welcomeToTheDungeon2);
        Util.sleeper(2000);
        System.out.println(UiString.welcomeToTheDungeon3);
        pressAKey();
    }

    public static void printLoadingIntro() {
        System.out.println("Loading game...");
        Util.sleeper(1800);
    }

    public static void pressAKey() {
        System.out.println("\n\n[press enter to continue]");
        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String randomSound() {
        int number = UiString.rand.nextInt(5);

        switch (number) {
            case 0:
                return UiString.randomSound1;
            case 1:
                return UiString.randomSound2;
            case 2:
                return UiString.randomSound3;
            case 3:
                return "";
            case 4:
                return "";

        }
        return "";
    }


    public static String randomVisual() {
        int number = UiString.rand.nextInt(6);

        switch (number) {
            case 0:
                return UiString.randomVisual1;
            case 1:
                return UiString.randomVisual2;
            case 2:
                return UiString.randomVisual3;
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "";

        }
        return "";
    }



}