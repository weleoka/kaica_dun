package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.util.MenuException;

public interface ActionEngineService {

    void playNew(Avatar avatar) throws MenuException;

    // Start playing
    void play(Avatar avatar) throws MenuException;

    // Resume playing
    void resume(Avatar avatar) throws MenuException;

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    void restart(Avatar avatar) throws MenuException;

}
