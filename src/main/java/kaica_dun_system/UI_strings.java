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
    public static String userIDValid = "Användarens ID är giltigt.";
    public static String userIDInvalid = "Användarens ID är ogiltigt.";
    public static String userNameToPasswordMismatch = "The username and password combination ar incorrect.";
    public static String successfullLogin = "Log-in successful.";
    public static String unsuccessfullLogin = "Log-in failed. Try again.";
    public static String logoutSuccessfull = "You are now logged out.";











    // Activity booking
    public static String userNotActive = "Ni har inte ett giltigt medlemskap.";
    public static String userSubscriptionUsable = "Abonnemang hittat och är giltigt.";
    public static String userSubscriptionNotUsable = "Abonnemang inte giltigt, skapa ett nytt abonnemang.";
    public static String noSuchActivity = "Den aktiviteten finns inte. Försök igen.";
    public static String selectActivityPrompt = "Välj aktivitet:";
    public static String selectRoomPlacePrompt = "Välj plats (ex. 2b eller 1d): ";
    public static String assignedUserToPlace = "Du har registrerats på platsen i rummet.";
    public static String assignedUserToPlaceFail = "Den platsen är redan bokad.";
    public static String bookingActivitySuccess = "Bra! Aktiviteten är bokad!";

    // Subscription management
    public static String selectSubscriptionPrompt = "Hur många månader väljer ni: ";
    public static String confirmSubscriptionPrompt = "Vill ni gå vidare med betalningen? (j/n): ";
    public static String assignedSubscriptionToUser = "Bra! Abonnemanget är fixat!";
    public static String assignedSubscriptionToUserFail = "Nej! Något fungerade inte. Ni har inget abonnemang fixat.";
    public static String noSuchSubscription = "Det abonnemanget finns inte.";
    public static String userSubscriptionNotFound = "Ni har inget giltigt abonnemang.";
    public static String membershipNeeded = "Ni behöver även ett medlemsskap för 100kr. ";
    public static String priceListPretty = "\n - - - WakeUp Gym Prislista - - - \n" +
            "Medlemskap – 100 SEK\n" +
            "1-2 månader – 400 SEK/månad\n" +
            "3-6 månader – 350 SEK/månad\n" +
            "7-12 månader – 300 SEK/månad\n" +
            "Längre än 12 månader – 250 SEK/månad";
    public static String subscriptionPriceTotal = "Totalt så blir priset för abonnemanget ";


}