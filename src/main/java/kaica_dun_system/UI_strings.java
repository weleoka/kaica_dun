package kaica_dun_system;

/**
 * Language specific strings for usage in WakeUP Gym UI class
 */
public class UI_strings
{
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


    // ACTION menus
    // In-game menu
    public static String menuHeader4 = "\n - - - Game Actions - - -";
    public static String lookAtMenuHeader = "\n - - - Look at something - - -";
    public static String moveMenuHeader = "\n - - - Time to move to the next room - - -";
    //public static String actionMenuRoomAlternatives = "\n1. Look at\n2. Battle\n3. Move\n9. Game menu";
    public static String actionMenuAlternatives = menuHeader3 + inGameMenuAlternatives + makeSelectionPrompt;








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




}