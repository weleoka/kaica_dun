package kaica_dun.dao;

import kaica_dun.entities_BACKUP.Item;
import kaica_dun.entities_BACKUP.PlayerAvatar;

public interface PlayerAvatarDao extends DaoGenericInterface<PlayerAvatar, Long> {

    /**
     *
     * @param currentPlayer
     * @param inventoryItem
     */
    void moveItem(PlayerAvatar currentPlayer, Item inventoryItem);
}


