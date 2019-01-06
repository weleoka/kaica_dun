package kaica_dun.util;

/**
 * Exception thrown when leaving the game from a conscious choice,
 * such as quitting through a menu selection.
 */
public class MenuException extends KaicaException {
    public MenuException(String message)
    {
        super(message);
    }
}
