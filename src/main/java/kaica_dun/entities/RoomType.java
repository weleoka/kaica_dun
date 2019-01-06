package kaica_dun.entities;

/**
 * This enum is for specifying specialities for a room.
 *
 * The enums are numbered so that changing or inserting types can be supported
 * with backwards compatibility to rooms already in persistent state.
 *
 * All rooms are created with STD type, it is up to the creator to then customize
 * the room by passing in a new RoomType enum instance. Previewed is that there may
 * be the need for the existence of many STD:ards. This is where the numberCode is used
 * for later being able to expand on the possibilities.
 *
 * todo: stored these enums in the database. This way enums can be configured and
 *  clients will respond with behaviour accordingly.
 */

public enum RoomType {
    NULL        ("a room used to block sectors of the dungeon matrix."),
    FIRST01     ("The first room in the dungeon"),
    LAST01      ("The last room in the dungeon"),
    STD01       ("A boring standard, nothing special room"),
    HARD01      ("a difficult room");


    private String name;
    private String description;


    RoomType(String description) {
        this.name = this.name();
        this.description = description;
    }


    public String getName() {
        return name;             // Compiler provided method for getting enum name.
    }
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String str = getName() + " : " + getDescription();

        return str;
    }
}
