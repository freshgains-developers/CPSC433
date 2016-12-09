/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

/**
 * @author Chris Kinzel, Eric Ma, Brenton Kruger, Micheal Friesen
 *         <p>
 *         Class to hold room data
 */
public class Room extends Entity {

    /**
     * @author Chris
     *         <p>
     *         Exception for when more than 2 people are assigned to a room
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
    private boolean hasFixedAssignments = false;

    // Constructor, accepts name and Room size
    public Room(String name, RoomSize size) {
        super(name);
        this.size = size;

        assignedPeople = new Person[2];
        assignedPeople[0] = null;
        assignedPeople[1] = null;
    }

    /** 
     * Copy constructor, * this is not a deep copy *
     * this method simply copies Person Room objects
     * in sufficient depth to preserve assignments
     */ 
    public Room(Room toCopy) {
        super(toCopy.getName());

        this.size = toCopy.getSize();
        this.hasFixedAssignments = toCopy.hasFixedAssignments();

        assignedPeople = new Person[2];
        assignedPeople[0] = null;
        assignedPeople[1] = null;

        if (toCopy.getAssignedPeople()[0] != null) {
            assignedPeople[0] = new Person(toCopy.getAssignedPeople()[0].getName());
        }
        if (toCopy.getAssignedPeople()[1] != null) {
            assignedPeople[1] = new Person(toCopy.getAssignedPeople()[1].getName());
        }
    }

    /**
     * Gets Room size
     * @return size of room
     */
    public RoomSize getSize() {
        return size;
    }

    /**
     * @return boolean true if either person in room is a manager, groupHead or projectHead
     */
    public boolean hasProod() {
        if (this.assignedPeople[0] == null) {
            //first not a person, second not a person
            return false;
        } else {
            if (this.assignedPeople[1] == null) {
                //check if first is a prood, second null
                return (this.assignedPeople[0].isManager() || this.assignedPeople[0].isProjectHead() || this.assignedPeople[0].isGroupHead());
            } else {
                //both people, check if either is a prood
                return (this.assignedPeople[0].isManager() || this.assignedPeople[0].isProjectHead() || this.assignedPeople[0].isGroupHead() || this.assignedPeople[1].isManager() || this.assignedPeople[1].isProjectHead() || this.assignedPeople[1].isGroupHead());
            }
        }
    }

    /**
     * Returns all people assigned to the room
     * @return array of Persons that are assigned to that room
     */
    public Person[] getAssignedPeople() {
        return assignedPeople;
    }

    public boolean isEmpty() {
        return ((assignedPeople[0] == null) && (assignedPeople[1] == null));
    }


    public void setSize(RoomSize size) {
        this.size = size;
    }

    
    /**
     * Sets if the Room has some fixed assignments or not. If it does then mutate wont change it
     */
    public void setHasFixedAssignments() {
        this.hasFixedAssignments = true;
    }

    
    /**
     * Checks if the room can be mutated
     * @return boolean
     */
    public boolean hasFixedAssignments() {
        return hasFixedAssignments;
    }

    /**
     * Returns if the room is completely fixed, or if it is only some variables
     * @return 
     */
    public boolean allFixed() {
        return isFull() && assignedPeople[0].getFixed() && assignedPeople[1].getFixed();
    }

    /**
     * Checks if the room is full
     * @return 
     */
    public boolean isFull() {
        if (this.assignedPeople[1] == null)
            return false;
        else
            return true;
    }

    /**
     * Adds Person p to this room if space is available,
     * otherwise throws FullRoomException and occupants
     * are unchanged
     */
    public void putPerson(Person p) throws FullRoomException {
        if (assignedPeople[0] == null) {
            if (p == null) {
                assignedPeople[0] = assignedPeople[1];
                assignedPeople[1] = null;
            } else {
                assignedPeople[0] = p;
            }
        } else if (assignedPeople[1] == null) {
            assignedPeople[1] = p;
        } else {
            throw new FullRoomException("Cannot assign more than 2 people to a room.");
        }
    }

    /**
     * Removes Person p from this room (if present)
     * @param p person to remove
     */
    public void removePerson(Person p) {
        if (assignedPeople[0] == p) {
            assignedPeople[0] = null;
        } else if (assignedPeople[1] == p) {
            assignedPeople[1] = null;
        }
    }

    /**
     * Removes all people from the room
     */
    public void clearRoom() {
        assignedPeople[0] = null;
        assignedPeople[1] = null;
    }
}
