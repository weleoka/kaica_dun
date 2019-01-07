package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.util.MenuException;

public interface ActionEngineService {

    // Prime the game world and get it ready
    void prime(Avatar avatar, Dungeon dungeon);

    void playNew() throws MenuException;

    // Start playing
    void play() throws MenuException;

    // Resume playing
    void resume() throws MenuException;

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    void restart() throws MenuException;

}
