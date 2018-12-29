package kaica_dun.dao.unusedDao;

import kaica_dun.dao.DaoGenericInterface;
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


