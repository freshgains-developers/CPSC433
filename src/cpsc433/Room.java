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
    public enum RoomSize {
        SMALL, MEDIUM, LARGE
    }
    
    private RoomSize size;
    private Person[] assignedPeople;
    
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
    
    // Setter for Room size
    public void setSize(RoomSize size) {
        this.size = size;
    }
    
    // Adds Person p to this room if space is available,
    // and returns true otherwise if the room is full
    // false is returned and the occupants are unchanged
    public boolean putPerson(Person p) {
        if(assignedPeople[0] == null) {
            assignedPeople[0] = p;
        } else if(assignedPeople[1] == null) {
            assignedPeople[1] = p;
        } else {
            return false;
        }
        
        return true;
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
