package kaica_dun_system.menus;

import kaica_dun.entities.Avatar;
import kaica_dun.entities.Item;
import kaica_dun.interfaces.Lootable;
import kaica_dun.util.MenuException;
import kaica_dun.util.Util;
import kaica_dun_system.ItemService;
import kaica_dun_system.ItemServiceImpl;
import kaica_dun_system.UiString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

/**
 * Actions concerning looting items from lootables
 *
 */
@Component
public class ActionMenuLootable extends ActionMenu {

    @Autowired
    ItemServiceImpl itemService;

    private String lootOutput;
    private HashMap<Integer, Item> lootOptions = new HashMap<>();
    private Set<Item> items;

    ActionMenuLootable() { }

    /**
     * Display the lootable action options.
     */
    public void display(Avatar avatar, Lootable lootable) throws MenuException {
        clearOptions();
        this.items = lootable.getContainer().getItems();
        buildLootOptions();
        String str = UiString.menuHeader5 + lootOutput + UiString.makeSelectionPrompt;
        int selection = getUserInput(lootOptions.keySet(), str);

        switch (selection) {
            case 1:
                selectLootOption(avatar, lootable);
                throw new MenuException("Left the loot menu after looting all items.");

            case 9:
                throw new MenuException("Left the loot menu.");
        }
    }


    /**
     * Items in a lootable container have to be described.
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
            output.append(String.format("\n[%s] - %s.", i, item.getItemName()));
            lootOptions.put(i, item);
        }
        lootOutput = output.toString();
    }


    /**
     * Handles the selection of looting things.
     */
    private void selectLootOption(Avatar avatar, Lootable lootable) {
        String str = UiString.lootMenuHeader + lootOutput + UiString.makeSelectionPrompt;
        //int sel = getUserInput(lootOptions.keySet(), str);
        //Item item = lootOptions.get(sel);
        System.out.println(str);
        System.out.println("Individual item picking disabled. Grabbing it all.");
        itemService.lootAll(avatar, lootable);
        //System.out.println(item.getDescription());
        Util.sleeper(1800);
    }

    /**
     * At every iteration of the loot loop make sure to clear the options.
     */
    private void clearOptions() {
        lootOptions.clear();
    }
}


/**
 * Handles the selection of looting things.
 */
/*
    private void selectLootOption(Avatar avatar) {
        String str = UiString.lookAtMenuHeader + lootOutput + UiString.makeSelectionPrompt;
        int sel = getUserInput(lootOptions.keySet(), str);
        Item item = lootOptions.get(sel);
        itemService.lootOne(item);
        System.out.println(item.getDescription());
        Util.sleeper(1400);
    }*/

