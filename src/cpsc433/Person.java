/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ArrayList;

/**
 *
 * @author Chris Kinzel, Eric Ma, Brenton Kruger, Micheal Friesen
 * An object that represents each person in the Sisyphus problem
 */
public class Person extends Entity{
    // Array of projects the person is a part of
    private ArrayList projects = null;
    // Array of groups the person is a part of
    private ArrayList groups = null;
    // Role booleans (default false)
    private boolean secretary = false;
    private boolean manager = false;
    private boolean hacker = false;
    private boolean projectHead = false;
    private boolean groupHead = false;
    private boolean researcher = false;
    private boolean smoker = false;
    
    // Fixed indicates if the persons assignments can be manipulated or not
    // If it was an initial assertion, that should not be changed, then it is
    // A fixed person
    private boolean fixed = false;

    // Assigned room or null if not assigned
    private Room assignedRoom = null;

    // ------ All of the setters / Adders for the instance variables --------

    public void addGroup(Group g){
        groups.add(g);
    }

    public void addProject( Project j){
        projects.add(j);
    }
    
    public ArrayList getProjects() {
        return projects;
    }
    
    public ArrayList getGroups() {
        return groups;
    }
    
    public void setSecretary(boolean b) {
        this.secretary = b;
    }


    public void setFixed(boolean b) {this.fixed = b;}

    
    
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

    public void setProjectHead(boolean b){
        this.projectHead = b;
    }

    public void setGroupHead(boolean b){
        this.groupHead = b;
    }

    // ----   All Get methods ----
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
    public boolean isProjectHead(){
        return projectHead;
    }
    public boolean isGroupHead(){
        return groupHead;
    }
    
    public boolean getFixed () {return this.fixed;}


    public Person(String name) {
        super(name);

        groups = new ArrayList();
        projects = new ArrayList();
    }

}
