package kaica_dun.entities;

import kaica_dun.entities.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class makeStaticDungeon {
    private Player player;
    private int roomRows = 5;
    private int roomColumns = 5;

    /**
     * Create a new dungeon with static structure for testing, belonging to a player, that we can then save to the db.
     *
     * @param player        the Player that the Dungeon belongs to
     */
    public makeStaticDungeon(Player player){
        this.player = player;
    }

    private Dungeon makeDungeon() {
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

        //make empty monster list
        List<Monster> emptyMonsters = new LinkedList<Monster>();

        // int roomIndex, Direction incomingDoor, List<Direction> exits, List<Monster> monsters
        // rooms named after their index in the 5x5 matrix.
        Room r0 = new Room(0, Direction.U, tmpS, emptyMonsters);
        rooms.add(0, r0);
        Room r5 = new Room(5, Direction.N, tmpE, emptyMonsters);
        rooms.add(5, r5);
        Room r6 = new Room(6, Direction.W, tmpE, emptyMonsters);
        rooms.add(6, r6);
        Room r7 = new Room(7, Direction.W, tmpS, emptyMonsters);
        rooms.add(7, r7);
        Room r12 = new Room(12, Direction.N, tmpE, emptyMonsters);
        rooms.add(12, r12);
        Room r13 = new Room(13, Direction.W, tmpW, emptyMonsters);
        rooms.add(13, r13);
        Room r14 = new Room(14, Direction.W, tmpN, emptyMonsters);
        rooms.add(14, r14);
        Room r9 = new Room(9, Direction.S, tmpU, emptyMonsters);
        rooms.add(9, r9);

        //Player player, int roomRows, int roomColumns, List<Room> rooms
        Dungeon dungeon = new Dungeon(player, roomRows, roomColumns, rooms);
        //Referential integrity, make sure the rooms point at the dungeon
        for (Room r : dungeon.getRooms()) {
            r.setDungeon(dungeon);
        }
        return dungeon;
    }
}
