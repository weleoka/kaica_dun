package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;

import java.util.List;

public interface GameService {


    Dungeon setDungeon(User user);

    // ********************** Persistence Methods ********************** //
    List<Avatar> fetchAvatarByUser(User user);

    boolean createStaticAvatar(User user);

    boolean createNewAvatar(String[] arr, User user);

    void setAvatar(Avatar avatar);

    String printAvatarListByUser(User user, boolean stdout);

}
