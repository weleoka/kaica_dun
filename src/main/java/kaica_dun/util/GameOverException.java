package kaica_dun.util;

/**
 * This exception signifies that the game is over.
 *
 * Usually because the players Avatar is mincemeat.
 */
public class GameOverException extends KaicaException {

    public GameOverException(String message)
    {
        super(message);
    }
}



