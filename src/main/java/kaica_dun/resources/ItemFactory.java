package kaica_dun.resources;

import kaica_dun.entities.Armor;
import kaica_dun.entities.Container;
import kaica_dun.entities.Item;
import kaica_dun.entities.Weapon;

import java.util.LinkedHashSet;
import java.util.Random;

public class ItemFactory {

    private final static Random random = new Random();

    ItemFactory() {}

    public static Weapon makeSmashanizer(Container container) {
        //Weapon deals 4-8 (min) or 5-11(max) damage
        int lowDamage = random.nextInt(2) + 4;       //4-5 minimum damage
        int damageRange = random.nextInt(3) + 4;     //4-6 damagerange

        return new Weapon("The Smashanizer", "Smashing!", lowDamage, damageRange);
    }

    public static Armor createDefaultArmor(Container container) {
        int randomizer = random.nextInt(2);

        if(randomizer == 1) {
            return new Armor("Bone Armor", "A suit of armor built from the broken bones of previous adventurers", 2);
        }

        return new Armor("Studded Leather", "A full suit of studded leather armor.", 2);

    }

    public static Weapon createDragonSlayer(Container container) {
        return new Weapon("'Wipe that Smug smile from your face', The Dragonslayer", "Smug dragons, beware", 8, 22);
    }
}
