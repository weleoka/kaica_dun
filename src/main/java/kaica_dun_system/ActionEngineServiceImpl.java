package kaica_dun_system;

import kaica_dun.dao.AvatarInterface;
import kaica_dun.dao.DungeonInterface;
import kaica_dun.dao.UserInterface;
import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;
import kaica_dun.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActionEngineServiceImpl implements ActionEngineService {

    User user;
    Avatar avatar;
    Dungeon dungeon;
    Room selectedRoom;

    @Autowired
    UserInterface ui;

    @Autowired
    AvatarInterface as;

    @Autowired
    DungeonInterface di;

    @Autowired
    ActionMenuRoom amr;



    // Prime the game world and get it ready
    // This could be the constructor as well but with Autowired it's better to use method injection in this case.
    public void prime(User user, Avatar avatar, Dungeon dungeon) {
        this.user = user;
        this.avatar = avatar;
        this.dungeon = dungeon;
    }


    // Start playing
    public void play() {
        amr.display();//this.user, this.avatar, this.dungeon);
    }


    // Resume playing
    public void resume() {

    }

    // Restart a dungeon (Random re-generation as original parameters are not saved...)
    // todo: implement original values for all rooms etc for real re-start.
    public void restart() {

    }







    // ********************** Accessor Methods ********************** //
    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    private void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    private void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Room getRoom() {
        return getAvatar().getCurrRoom();
    }

    private void setRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }
}
