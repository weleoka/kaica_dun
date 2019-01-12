package kaica_dun_system;

import kaica_dun.util.MenuException;

public interface ActionEngineService {

    void playNew() throws MenuException;

    // Start playing
    void play() throws MenuException;

    // Resume playing
    void resume() throws MenuException;

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    void restart() throws MenuException;

}
