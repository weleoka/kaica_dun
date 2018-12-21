package kaica_dun.entities2;

import java.util.Random;

public class Monster2Factory {

    Monster2Factory() {}

    public static Monster2 makeMonster() {
        Random random = new Random();
        int armor = random.nextInt(3);
        int maxHealth = random.nextInt(6) + 10;     //10-15 health
        int currHealth = maxHealth;                         //10-15 health
        int damage = random.nextInt(3) + 2;         //2-4 damage
        String type = "Orc";                                //TODO possible enum
        String description = "It's a filthy " + type;
        String name = type;                                 //TODO change for boss monsters

        return new Monster2(armor, maxHealth, currHealth, damage, type, description, name);
    }
}
