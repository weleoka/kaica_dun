package kaica_dun_system;

public interface ActionEngineService {

    // Prime the game world and get it ready
    void prime();

    // Start playing
    void play();

    // Resume playing
    void resume();

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    void restart();

}
