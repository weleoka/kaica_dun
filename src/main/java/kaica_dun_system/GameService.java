package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;

import java.util.List;

public interface GameService {


    Dungeon createDungeon(User user);

    // ********************** Persistence Methods ********************** //
    List<Avatar> fetchAvatarByUser(User user);

    boolean createNewAvatar(String[] arr, User user);

    void setAvatar(Avatar avatar);
}