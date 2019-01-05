package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;

public interface ActionEngineService {

    // Prime the game world and get it ready
    void prime(Avatar avatar, Dungeon dungeon);


    // Start playing
    void play();

    // Resume playing
    void resume();

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    void restart();

}
