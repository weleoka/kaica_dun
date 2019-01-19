package kaica_dun_system.menus;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Item;
import kaica_dun.interfaces.Lootable;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import kaica_dun_system.UiString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * Actions concerning looting items from lootables
 *
 */
@Component
public class ActionMenuLootable extends ActionMenu {

    private String lootOutput;
    private HashMap<Integer, Item> lootOptions = new HashMap<>();
    private Set<Item> items;


    /**
     * Display the lootable action options.
     */
    public void display(Avatar avatar, Lootable lootable) throws MenuException {
        int selection;

        clearOptions();

        this.items = lootable.getContainer().getItems();

        buildLootOptions();

        //buildMainOptions();

        String str = UiString.menuHeader5 + lootOutput + UiString.makeSelectionPrompt;
        selection = getUserInput(lootOptions.keySet(), str);

        switch (selection) {
            case 1:
                selectLootOption(avatar);
                break;

            case 9:
                throw new MenuException("Left the loot menu.");
                //amr.display(avatar);
                //break;
        }

    }


    /**
     * Different things in a room need to be described.
     * These are: monsters, directions, chests, other avatars?
     *
     * todo: implement looking at items and other things, not only monsters.
     */
    private void buildLootOptions() {
        StringBuilder output = new StringBuilder();
        lootOptions = new HashMap<>();
        log.debug("There are {} items in the lootable.", items.size());
        int i = 0;

        for (Item item : items) {
            i++;
            output.append(String.format("\n[%s] - %s.", i, item.getDescription()));
            lootOptions.put(i, item);
        }
        lootOutput = output.toString();
    }


    /**
     * Handles the selection of looting things.
     */
    private void selectLootOption(Avatar avatar) {
        String str = UiString.lookAtMenuHeader + lootOutput + UiString.makeSelectionPrompt;
        int sel = getUserInput(lootOptions.keySet(), str);
        Item item = lootOptions.get(sel);
        System.out.println(item.getDescription());
        Util.sleeper(1400);
    }


    /**
     * At every iteration of the loot loop make sure to clear the options.
     */
    private void clearOptions() {
        lootOptions.clear();
    }
}
