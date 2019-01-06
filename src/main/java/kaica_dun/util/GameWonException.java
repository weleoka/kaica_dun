package kaica_dun.util;

/**
 * Exception thtrown when the player completes the game and
 * has achieved the ultimate win scenario.
 *
 */
public class GameWonException extends KaicaException {


    public GameWonException(String message)
    {
        super(message);
    }
}
