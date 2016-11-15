/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 *
 * @author Chris
 * 
 * Class to hold room data
 */
public class Room extends Entity {
    
    /**
     * @author Chris
     * 
     * Exception for when more than 2 people are assigned to a room
     */
    public class FullRoomException extends Exception {
        public FullRoomException() {
            super();
        }

        public FullRoomException(String message) {
            super(message);
        }

        public FullRoomException(String message, Throwable cause) {
            super(message, cause);
        }

        public FullRoomException(Throwable cause) {
            super(cause);
        }
    }
    
    public enum RoomSize {
        SMALL, MEDIUM, LARGE
    }
    
    private RoomSize size;
    private final Person[] assignedPeople;
    
    // Constructor, accepts name and Room size
    public Room(String name, RoomSize size) {
        super(name);
        this.size = size;
        
        assignedPeople = new Person[2];
        assignedPeople[0] = null;
        assignedPeople[1] = null;
    }
    
    // Getter for Room size
    public RoomSize getSize() {
        return size;
    }
    
    // Getter for assigned people
    public Person[] getAssignedPeople() {
        return assignedPeople;
    }
    
    // Setter for Room size
    public void setSize(RoomSize size) {
        this.size = size;
    }
    
    // Adds Person p to this room if space is available,
    // otherwise throws FullRoomException and occupants
    // are unchanged
    public void putPerson(Person p) throws FullRoomException {
        if(assignedPeople[0] == null) {
            assignedPeople[0] = p;
        } else if(assignedPeople[1] == null) {
            assignedPeople[1] = p;
        } else {
            throw new FullRoomException("Cannot assign more than 2 people to a room.");
        }
    }
    
    // Removes Person p from this room (if present)
    public void removePerson(Person p) {
        if(assignedPeople[0] == p) {
            assignedPeople[0] = null;
        } else if(assignedPeople[1] == p) {
            assignedPeople[1] = null;
        }
    }
    
    // Removes all people from the room
    public void clearRoom() {        
        assignedPeople[0] = null;
        assignedPeople[1] = null;
    }
}
