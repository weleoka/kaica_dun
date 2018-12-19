package main.java.kaica_dun.Entities;

public class RoomFactory {

        public static Room createRoom (Direction incomingDoor, boolean isStarter) {
            return new Room(incomingDoor, isStarter);
        }
}
