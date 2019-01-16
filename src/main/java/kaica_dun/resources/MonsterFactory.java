package kaica_dun.resources;

import kaica_dun.entities.Monster;

import java.util.LinkedHashSet;
import java.util.Random;

class MonsterFactory {
    private final static Random random = new Random();

    MonsterFactory() {}

    public static Monster makeOrc() {
        int armor = random.nextInt(3);
        int maxHealth = random.nextInt(6) + 10;     //10-15 health
        int currHealth = maxHealth;
        int damage = random.nextInt(3) + 4;         //4-6 damage
        String type = "Orc";                                //TODO possible enum
        String description = "It's a filthy " + type;
        String name = type;                                 //TODO change for boss monsters

        return new Monster(name, description, type, currHealth, maxHealth, damage, armor);
    }

    private static Monster makeOrcBoss() {
        int armor = random.nextInt(3) + 3;          //3-5 armor
        int maxHealth = random.nextInt(6) + 30;     //30-35 health
        int currHealth = maxHealth;
        int damage = random.nextInt(5) + 6;         //6-10 damage
        String type = "Orc";                                //TODO possible enum
        String description = "Looming a head above the other greenskins, this large Orc wears heavy armor and his axe" +
                " gives of a faint red glow." + " As he turns towards you, you can see his left eye socket is an empty black pit.";
        String name = "Murash One-Eye";

        return new Monster(name, description, type, currHealth, maxHealth, damage, armor);
    }

    public static Monster makeMonsterEasy() {
        Monster monster;
        if (random.nextInt(1) == 0) {
            monster = makeSkeleton();
        } else {
            monster = makeGoblin();
        }
        return monster;
    }

    private static Monster makeSkeleton() {
        int armor = random.nextInt(1);
        int maxHealth = random.nextInt(5) + 7;      //7-12 health
        int currHealth = maxHealth;
        int damage = random.nextInt(2) + 2;         //2-3 damage
        String type = "Skeleton";                           //TODO possible enum
        String description = "An animated mess of bones.";
        String name = type;

        return new Monster(name, description, type, currHealth, maxHealth, damage, armor);
    }

    public static Monster makeDragonBoss() {
        int armor = 6;
        int maxHealth = 100;
        int currHealth = maxHealth;
        int damage = 12;
        String type = "Dragon";                             //TODO possible enum
        String description = "Large and fearsome, this fire-breathing dragon is covered in gleaming green scales." +
                " Also: quite smug. Unlike his cousin, which you may have heard of.";
        String name = "Smug, cousin of Smaug";

        return new Monster(name, description, type, currHealth, maxHealth, damage, armor);
    }

    private static Monster makeGoblin() {
        int armor = 0;
        int maxHealth = random.nextInt(2) + 6;     //6-7 health
        int currHealth = maxHealth;
        int damage = random.nextInt(3) + 3;         //3-5 damage
        String type = "Goblin";                                //TODO possible enum
        String description = "A frail greenskin. It jumps around excitedly, waving a shining scimitar.";
        String name = type;

        return new Monster(name, description, type, currHealth, maxHealth, damage, armor);
    }

    private static void orcOrGoblin(LinkedHashSet<Monster> monsters) {
        if (random.nextInt(2) == 0) {
            monsters.add(makeGoblin());
        } else {
            monsters.add(makeOrc());
        }
    }

    public static LinkedHashSet<Monster> makeEasyGreenskinGroup() {
        LinkedHashSet<Monster> monsters = new LinkedHashSet<Monster>();
        //0-4, not normal distribution.
        int numberMonsters = random.nextInt(3) + random.nextInt(2) + random.nextInt(2);

        switch (numberMonsters) {
            case 4:
                monsters.add(makeGoblin());

            case 3:
                orcOrGoblin(monsters);

            case 2:
                orcOrGoblin(monsters);

            case 1:
                orcOrGoblin(monsters);

            default:
        }

        if (random.nextInt(20) == 19) {
            monsters.add(makeOrcBoss());
        }

        return monsters;
    }
}