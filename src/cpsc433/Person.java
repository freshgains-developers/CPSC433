/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;
import java.util.ArrayList;

/**
 *
 * @author Micheal
 * An object that represents each person in the Sisyphus problem
 */
public class Person extends Entity{
    // Array of projects the person is assigned to
    private Project[] projects = null;
    // Array of groups the person is associated with
    private ArrayList groups = new ArrayList(null); 
    // Role booleans (default false)
    private boolean secretary = false;
    private boolean manager = false;
    private boolean hacker = false;
    
    private boolean researcher = false;
    // Smoker (default false)
    private boolean smoker = false;
    
    /**
     * Accessor for groups array
     * @param g group to add to person array
     */
    public void addGroup(Group g){
        groups.add(g);
    }
    
    // Assigned room or null if not assigned
    private Room assignedRoom = null;

    // Set methods for the private variables of person
    public void setSecretary(boolean b) {
        this.secretary = b;   
    }

    public void setManager(boolean b) {
        this.manager = b;   
    }

    public void setHacker(boolean b) {
        this.hacker = b;   
    }

    public void setResearcher(boolean b) {
        this.researcher = b;   
    }

    public void setSmoker(boolean b) {
        this.smoker = b;   
    }
    
    public void assignToRoom(Room room) {
        assignedRoom = room;
    }
    
    // Get methods
    public boolean isSecretary(){
        return secretary;
    }
    
    public boolean isManager(){
        return manager;
    }
    
    public boolean isHacker(){
        return hacker;
    }
    
    public boolean isResearcher(){
        return researcher;
    }
    
    public boolean isSmoker(){
        return smoker;
    }
    
    public Room assignedRoom() {
        return assignedRoom;
    } 
    
    public Person(String name) {
        super(name);
    }

} 