package kaica_dun.entities;

public class RoomFactory {

        public static Room createRoom (Direction incomingDoor, boolean isStarter) {
            return new Room(incomingDoor, isStarter);
        }
}
