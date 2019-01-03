package kaica_dun_system;

import kaica_dun.entities.Avatar;

import java.util.List;

public interface GameService {

    List<Avatar> fetchAvatarByUser(User user);

    boolean createNewAvatar(String[] arr, User user);

    void setAvatar(Avatar avatar);
}
