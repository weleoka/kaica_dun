package kaica_dun.entities2;

import kaica_dun.entities.Monster;

import java.util.Random;

class MonsterFactory {

    MonsterFactory() {}

    public static Monster makeMonster() {
        Random random = new Random();

        int armor = random.nextInt(3);
        int maxHealth = random.nextInt(6) + 10;     //10-15 health
        int currHealth = maxHealth;                         //10-15 health
        int damage = random.nextInt(3) + 2;         //2-4 damage
        String type = "Orc";                                //TODO possible enum
        String description = "It's a filthy " + type;
        String name = type;                                 //TODO change for boss monsters

        return new Monster(armor, maxHealth, currHealth, damage, description, name, type);
    }
}


/*    public Monster(int armor, int currHealth, int damage, String description, int maxHealth, String name, String type) {
        this.armor = armor;
        this.currHealth = currHealth;
        this.damage = damage;
        this.description = description;
        this.maxHealth = maxHealth;
        this.name = name;
        this.type = type;
    }*/