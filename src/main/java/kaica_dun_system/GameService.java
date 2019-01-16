package kaica_dun_system;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Dungeon;

import java.util.HashMap;
import java.util.List;

public interface GameService {


    Dungeon makeStaticDungeon();


    // ********************** Persistence Methods ********************** //
    List<Avatar> fetchAvatarByUser(User user);

    Avatar createStaticAvatar(User user);

    boolean createNewAvatar(String[] arr, User user);

    HashMap<Integer, String> buildAvatarOptions(User user);

}
