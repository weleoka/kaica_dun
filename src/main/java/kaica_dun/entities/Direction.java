package kaica_dun.entities;

/**
 * Clockwise representation of the four directions with a string for printing and a number for basic algebra.
 */
public enum Direction {
    N    ("North", 0),
    E    ("East", 1),
    S    ("South", 2),
    W    ("West", 3),
    U    ("Up and out", 4);     //TODO not sure we want this

    private final String name;  //String representation of direction
    private final int directionNumber; //number representing the direction for simplified algebra

    Direction(String namn, int directionNumber) {
        this.name = namn;
        this.directionNumber = directionNumber;
    }

    public int getDirectionNumber() {
        return directionNumber;
    }

    //Override to print this enum as a full string
    @Override
    public String toString(){
        return this.name;
    }
}
