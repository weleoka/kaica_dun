package kaica_dun.resources;

import kaica_dun.entities.*;
import kaica_dun_system.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class makeStaticDungeon {
    private Random rand = new Random();
    private MonsterFactory mf = new MonsterFactory();
    private static int roomRows = 5;
    private static int roomColumns = 5;

    private static final Logger log = LogManager.getLogger();


    public static Dungeon buildDungeon() {
        List<Room> rooms = new ArrayList<Room>();
        //loop for room-list creation, initialised to null.
        for (int i = 0; i < (roomRows * roomColumns); i++) {
            rooms.add(null);
        }

        // int roomIndex, Direction incomingDoor, List<Direction> exits, List<Monster> monsters
        // rooms named after their index in the 5x5 matrix.
        List<Direction> ex0 = new LinkedList<Direction>();
        ex0.add(Direction.U);
        ex0.add(Direction.S);
        Room r0 = new Room(0, Direction.U, ex0, MonsterFactory.makeEasyGreenskinGroup());
        r0.setRoomType(RoomType.FIRST01);
        updateMonsters(r0);
        rooms.set(0, r0);

        List<Direction> ex5 = new LinkedList<Direction>();
        ex5.add(Direction.N);
        ex5.add(Direction.E);
        Room r5 = new Room(5, Direction.N, ex5, MonsterFactory.makeEasyGreenskinGroup());
        updateMonsters(r5);
        rooms.set(5, r5);

        List<Direction> ex6 = new LinkedList<Direction>();
        ex6.add(Direction.W);
        ex6.add(Direction.E);
        Room r6 = new Room(6, Direction.W, ex6, MonsterFactory.makeEasyGreenskinGroup());
        updateMonsters(r6);
        rooms.set(6, r6);

        List<Direction> ex7 = new LinkedList<Direction>();
        ex7.add(Direction.W);
        ex7.add(Direction.S);
        Room r7 = new Room(7, Direction.W, ex7, MonsterFactory.makeEasyGreenskinGroup());
        updateMonsters(r7);
        rooms.set(7, r7);

        List<Direction> ex12 = new LinkedList<Direction>();
        ex12.add(Direction.N);
        ex12.add(Direction.E);
        Room r12 = new Room(12, Direction.N, ex12, MonsterFactory.makeEasyGreenskinGroup());
        r12.setRoomType(RoomType.HARD01);
        updateMonsters(r12);
        rooms.set(12, r12);


        List<Direction> ex13 = new LinkedList<Direction>();
        ex13.add(Direction.W);
        ex13.add(Direction.E);
        Room r13 = new Room(13, Direction.W, ex13, MonsterFactory.makeEasyGreenskinGroup());
        updateMonsters(r13);
        rooms.set(13, r13);

        List<Direction> ex14 = new LinkedList<Direction>();
        ex14.add(Direction.W);
        ex14.add(Direction.N);
        Room r14 = new Room(14, Direction.W, ex14, MonsterFactory.makeEasyGreenskinGroup());
        updateMonsters(r14);
        rooms.set(14, r14);

        List<Direction> ex9 = new LinkedList<Direction>();
        ex9.add(Direction.S);
        ex9.add(Direction.U);
        Room r9 = new Room(9, Direction.S, ex9, makeSmug());
        r9.setRoomType(RoomType.LAST01);
        updateMonsters(r9);
        rooms.set(9, r9);

        //User user, int roomRows, int roomColumns, List<Room> rooms
        log.debug("Starting/building dungeon instance...");
        Dungeon dungeon = new Dungeon(roomRows, roomColumns, rooms);

        //Referential integrity, make sure the rooms point at the dungeon
        for (Room r : dungeon.getRooms()) {
            if (r != null) {
                r.setDungeon(dungeon);
            }
        }

        return dungeon;
    }

    private List<Monster> randomMonsterList() {
        List<Monster> randomMonsters = new LinkedList<Monster>();

        for (int i = 0; i <= rand.nextInt(4); i++) {
            randomMonsters.add(mf.makeOrc());
        }
        //TODO ADD ROOM TO MONSTER INSTANCES!
        return randomMonsters;
    }

    private static List<Monster> makeSmug() {
        List<Monster> smug = new LinkedList<Monster>();
        smug.add(MonsterFactory.makeDragonBoss());
        return smug;
    }


    private static void updateMonsters(Room r) {
        for (Monster m : r.getMonsters()) {
            log.debug("updateMonsters: '{}' to the room '{}'.", m.getDescription(), r.getRoomIndex());
            m.setRoom(r);
        }
    }
}
