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
    
    // Constructor, accepts name and Room size
    public Room(String name, RoomSize size) {
        super(name);
        this.size = size;
    }
    
    // Getter for Room size
    public RoomSize getSize() {
        return size;
    }
    
    // Setter for Room size
    public void setSize(RoomSize size) {
        this.size = size;
    }
}
