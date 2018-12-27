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
    private User user;
    private Random rand = new Random();
    private MonsterFactory mf = new MonsterFactory();
    private int roomRows = 5;
    private int roomColumns = 5;

    private static final Logger log = LogManager.getLogger();

    /**
     * Create a new dungeon with static structure for testing, belonging to a user, that we can then save to the db.
     *
     * @param user        the User that the Dungeon belongs to
     */
    public makeStaticDungeon(User user){
        this.user = user;
    }

    public Dungeon buildDungeon() {
        List<Room> rooms = new ArrayList<Room>();
        //loop for room-list creation, initialised to null.
        for (int i = 0; i < (roomRows * roomColumns); i++) {
            rooms.add(null);
        }
        //make exit lists for single exits
        List<Direction> tmpN = new LinkedList<>();
        tmpN.add(Direction.N);
        List<Direction> tmpE = new LinkedList<>();
        tmpE.add(Direction.E);
        List<Direction> tmpS = new LinkedList<>();
        tmpS.add(Direction.S);
        List<Direction> tmpW = new LinkedList<>();
        tmpW.add(Direction.W);
        List<Direction> tmpU = new LinkedList<>();
        tmpU.add(Direction.U);

        // int roomIndex, Direction incomingDoor, List<Direction> exits, List<Monster> monsters
        // rooms named after their index in the 5x5 matrix.
        Room r0 = new Room(0, Direction.U, tmpS, randomMonsterList());
        updateMonsters(r0);
        rooms.add(0, r0);
        Room r5 = new Room(5, Direction.N, tmpE, randomMonsterList());
        updateMonsters(r5);
        rooms.add(5, r5);
        System.out.println(r5.getMonsters().get(0).getRoom().getRoomId());
        Room r6 = new Room(6, Direction.W, tmpE, randomMonsterList());
        updateMonsters(r6);
        rooms.add(6, r6);
        Room r7 = new Room(7, Direction.W, tmpS, randomMonsterList());
        updateMonsters(r7);
        rooms.add(7, r7);
        Room r12 = new Room(12, Direction.N, tmpE, randomMonsterList());
        updateMonsters(r12);
        rooms.add(12, r12);
        Room r13 = new Room(13, Direction.W, tmpW, randomMonsterList());
        updateMonsters(r13);
        rooms.add(13, r13);
        Room r14 = new Room(14, Direction.W, tmpN, randomMonsterList());
        updateMonsters(r14);
        rooms.add(14, r14);
        Room r9 = new Room(9, Direction.S, tmpU, randomMonsterList());
        updateMonsters(r9);
        rooms.add(9, r9);

        //User user, int roomRows, int roomColumns, List<Room> rooms
        log.debug("Starting/building dungeon instance...");
        Dungeon dungeon = new Dungeon(this.getUser(), roomRows, roomColumns, rooms);

        //Referential integrity, make sure the rooms point at the dungeon
        for (Room r : dungeon.getRooms()) {
            if (r != null) {
                r.setDungeon(dungeon);
            }
        }
        return dungeon;
    }

    public User getUser() { return this.user; }

    public List<Monster> randomMonsterList() {
        List<Monster> randomMonsters = new LinkedList<Monster>();

        for (int i = 0; i <= rand.nextInt(4); i++) {
            randomMonsters.add(mf.makeMonster());
        }
        //TODO ADD ROOM TO MONSTER INSTANCES!
        return randomMonsters;
    }

    public void updateMonsters(Room r) {
        for (Monster m : r.getMonsters()) {
            log.debug("updateMonsters: '{}' to the room '{}'.", m.getDescription(), r.getRoomIndex());
            m.setRoom(r);
        }
    }
}
