package kaica_dun_system;

/**
 * Language specific strings for usage in WakeUP Gym UI class
 */
public class UI_strings
{
    // Menus and selection
    public static String menuSelectionFailed = "Ogiltigt alternativ";
    public static String menuHeader = "\n - - - WakeUP Gym - - -";
    public static String mainMenuAlternatives = "\n1.Logga in\n2.Registrera användare\n9.Avsluta";
    public static String loggedInMenuAlternatives = "\n1.Boka plats på aktivitet\n2.Hantera abonnemang och medlemskap\n9.Logga ut";
    public static String makeSelectionPrompt = "\n\nVar god välj ett alternativ:";
    public static String mainMenu = menuHeader + mainMenuAlternatives + makeSelectionPrompt;
    public static String loggedInMenu = menuHeader + loggedInMenuAlternatives + makeSelectionPrompt;
    public static String goodbyeString = "\n\nHej då, och tack för besöket.";

    // User creation
    public static String createUserHeader = "--> Skapa Ny Användare";
    public static String promptUserName = "Skriv användarnamn:";
    public static String promptUserID = "Skriv användar id (YYMMDD-XXX):";
    public static String userNameExists = "Användarnamnet finns redan.";
    public static String userIDExists = "Användarens ID finns redan.";
    public static String createUserSuccess = "Användaren skapades.";
    public static String createUserFail = "Något gick fel. Försök igen.";

    // User authorisation
    public static String userNotAuthenticated = "Ej inloggad.";
    public static String userNameFound = "Användaren hittades i databasen.";
    public static String userNameNotFound = "Användaren hittades inte i databasen.";
    public static String userIDValid = "Användarens ID är giltigt.";
    public static String userIDInvalid = "Användarens ID är ogiltigt.";
    public static String userNameToIDMissmatch = "Användarens Namn och ID är inte ett giltigt par.";
    public static String successfullLogin = "Log-in lyckades. Välkommen.";
    public static String unsuccessfullLogin = "Log-in misslyckades. Försök igen.";
    public static String logoutSuccessfull = "Ni är nu utloggad.";

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