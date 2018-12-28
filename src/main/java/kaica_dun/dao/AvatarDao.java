package kaica_dun.dao;

import kaica_dun.entities.Item;
import kaica_dun.entities.Avatar;

public interface AvatarDao extends DaoGenericInterface<Avatar, Long> {

    /**
     *
     * @param currentPlayer
     * @param inventoryItem
     */
    void moveItem(Avatar currentPlayer, Item inventoryItem);
}


