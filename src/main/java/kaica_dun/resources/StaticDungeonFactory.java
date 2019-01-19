package kaica_dun.resources;

import kaica_dun.dao.DungeonInterface;
import kaica_dun.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public final class StaticDungeonFactory {
    private Random rand = new Random();
    private MonsterFactory mf = new MonsterFactory();
    private static int roomRows = 5;
    private static int roomColumns = 5;
    private static DungeonInterface di;

    private static final Logger log = LogManager.getLogger();

    protected StaticDungeonFactory() {}

    public static Dungeon buildDungeon() {
        Set<Chest> chests = new LinkedHashSet<>();
        chests.add(new Chest(true));

        // rooms named after their index in the 5x5 matrix.
        Set<Direction> ex0 = new LinkedHashSet<Direction>();
        ex0.add(Direction.U);
        ex0.add(Direction.S);
        Room r0 = new Room(0, Direction.U, ex0, MonsterFactory.makeEasyGreenskinGroup(), chests);
        r0.setRoomType(RoomType.FIRST01);
        updateMonsters(r0);
        updateChest(r0);

        Set<Direction> ex5 = new LinkedHashSet<Direction>();
        ex5.add(Direction.N);
        ex5.add(Direction.E);
        Room r5 = new Room(5, Direction.N, ex5, MonsterFactory.makeEasyGreenskinGroup(), null);
        updateMonsters(r5);

        Set<Direction> ex6 = new LinkedHashSet<Direction>();
        ex6.add(Direction.W);
        ex6.add(Direction.E);
        Room r6 = new Room(6, Direction.W, ex6, MonsterFactory.makeEasyGreenskinGroup(), null);
        updateMonsters(r6);

        Set<Direction> ex7 = new LinkedHashSet<Direction>();
        ex7.add(Direction.W);
        ex7.add(Direction.S);
        Room r7 = new Room(7, Direction.W, ex7, MonsterFactory.makeEasyGreenskinGroup(), null);
        updateMonsters(r7);

        Set<Direction> ex12 = new LinkedHashSet<Direction>();
        ex12.add(Direction.N);
        ex12.add(Direction.E);
        Room r12 = new Room(12, Direction.N, ex12, MonsterFactory.makeEasyGreenskinGroup(), null);
        r12.setRoomType(RoomType.HARD01);
        updateMonsters(r12);

        Set<Direction> ex13 = new LinkedHashSet<Direction>();
        ex13.add(Direction.W);
        ex13.add(Direction.E);
        Room r13 = new Room(13, Direction.W, ex13, MonsterFactory.makeEasyGreenskinGroup(), null);
        updateMonsters(r13);

        Set<Direction> ex14 = new LinkedHashSet<Direction>();
        ex14.add(Direction.W);
        ex14.add(Direction.N);
        Room r14 = new Room(14, Direction.W, ex14, MonsterFactory.makeEasyGreenskinGroup(), null);
        updateMonsters(r14);

        Set<Direction> ex9 = new LinkedHashSet<Direction>();
        ex9.add(Direction.S);
        ex9.add(Direction.U);
        Room r9 = new Room(9, Direction.S, ex9, makeSmug(), null);
        r9.setRoomType(RoomType.LAST01);
        updateMonsters(r9);

        //Add rooms to temporary list for orderly transfer into Set
        List<Room> tmpRooms = new ArrayList<>(30);
        for (int i = 0; i < (roomRows * roomColumns); i++) {
            tmpRooms.add(null);
        }
        tmpRooms.set(0, r0);
        tmpRooms.set(5, r5);
        tmpRooms.set(6, r6);
        tmpRooms.set(7, r7);
        tmpRooms.set(12, r12);
        tmpRooms.set(13, r13);
        tmpRooms.set(14, r14);
        tmpRooms.set(9, r9);

        Set<Room> rooms = new LinkedHashSet<Room>();
        //loop for room-list creation, initialised to null.
        for (int i = 0; i < (roomRows * roomColumns); i++) {
            if(tmpRooms.get(i) != null) {
                rooms.add(tmpRooms.get(i));
            } else {
                rooms.add(new Room(i , null, null, new LinkedHashSet<Monster>(), null));
            }
        }

        //User user, int roomRows, int roomColumns, List<Room> rooms
        log.debug("Starting/building dungeon instance...");
        Dungeon dungeon = new Dungeon(roomRows, roomColumns, rooms);
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

    private static Set<Monster> makeSmug() {
        Set<Monster> smug = new LinkedHashSet<Monster>();
        smug.add(MonsterFactory.makeDragonBoss());
        return smug;
    }


    private static void updateMonsters(Room r) {
        for (Monster m : r.getMonsters()) {
            log.debug("updateMonsters: '{}' to the room '{}'.", m.getType(), r.getRoomIndex());
            m.setRoom(r);
        }
    }

    private static void updateChest(Room r) {
        for (Chest c : r.getChests()) {
            log.debug("updated chests in the room '{}'.", r.getRoomIndex());
            c.setRoom(r);
        }
    }
}
