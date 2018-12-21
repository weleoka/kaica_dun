package kaica_dun.dao;

import kaica_dun.entities.Item;
import kaica_dun.entities.PlayerAvatar;

import java.util.List;

public interface PlayerAvatarDao extends DaoGenericInterface<PlayerAvatar, Long> {

    /**
     *
     * @param currentPlayer
     * @param inventoryItem
     */
    void moveItem(PlayerAvatar currentPlayer, Item inventoryItem);
}


