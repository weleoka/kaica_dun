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
    public static String loggedInMenuAlternatives = "\n1. New Game\n2. Create Avatar\n9. Log out";
    public static String loggedInMenu = menuHeader2 + loggedInMenuAlternatives + makeSelectionPrompt;

    // In-game menu
    public static String menuHeader3 = "\n - - - Game Menu - - -";
    public static String inGameMenuAlternatives = "\n1. Resume\n2. Restart\n3.Save and Quit\n9. Quit Game";
    public static String inGameMenu = menuHeader3 + inGameMenuAlternatives + makeSelectionPrompt;



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



}