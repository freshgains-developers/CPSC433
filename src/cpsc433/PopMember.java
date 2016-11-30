/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import cpsc433.Room.RoomSize;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.AbstractQueue<E>;

/**
 *
 * @author Brenton effing Kruger
 * Container for a complete solution
 *
 */
public class PopMember{
    private HashSet<SymmetricPair<Person, Person>> worksWith = null;
    private HashMap<String, Person> people = null;
    private HashMap<String, Group> groups = null;
    private HashMap<String, Project> projects = null;
    private HashMap<String, Room> rooms = null;
    private HashSet<SymmetricPair<Room, Room>> closeTo = null;
    private HashMap<Person, Room> assignments;


    public void PopMember(HashSet worksWith, HashMap people, HashMap groups, HashMap projects, HashMap rooms,HashSet closeTo){
        //initialize population randomly.
        Random randGen = new Random();
        HashMap<Person, Room> assignments = new HashMap();
        Iterator<Person> peopleIter = people.values().iterator();
        this.worksWith = worksWith;
        this.people = people;
        this.groups = groups;
        this.projects = projects;
        this.rooms = rooms;
        this.closeTo = closeTo;

        AbstractQueue<People> managerQ = new AbstractQueue();
        AbstractQueue<People> groupHeadQ = new AbstractQueue();
        AbstractQueue<People> projectHeadQ = new AbstractQueue();
        AbstractQueue<People> secretaryQ = new AbstractQueue();
        AbstractQueue<People> plebQ = new AbstractQueue();



        //identify all managers, group heads, project heads, secretaries and plebs and assign to proper queues
        while peopleIter.hasNext(){
            Person tempPerson = peopleIter.next()
            switch (tempPerson){
                case tempPerson.isManager(): managerQ.add(tempPerson);
                    break;
                case tempPerson.isGroupHead(): groupHeadQ.add(tempPerson);
                    break;
                case tempPerson.isProjectHead(): projectHeadQ.add(tempPerson);
                    break;
                case tempPerson.isSecretary(): secretaryQ.add(tempPerson);
                    break;
                default: plebQ.add(tempPerson)
                    break;
            }
        }

        //assign randomly.
        while (managerq.peek() != null){
            Person tempPerson = managerq.remove();
            //Room tempRoom =
            try{
            tempRoom.putPerson(tempPerson);
            }
            catch(FullRoomException e){


            }
            assignments.put(tempPerson.getName(), tempRoom)
        }
        //while managers is not empty, assign to tempPerson
        //getRandom Room with no respect to size.
    }
    
    public int score() {
        int score = 0;
        
        Iterator <Room> roomIter;
        roomIter = assignments.values().iterator();
        
        while(roomIter.hasNext()) {
            Room r = roomIter.next();
            
            Person p1 = r.getAssignedPeople()[0];
            Person p2 = r.getAssignedPeople()[1];
            
            // 16) Two people shouldn't share a small room
            if(p1 != null && p2 != null && r.getSize() == RoomSize.SMALL) {
                score -= 25;
            }
            
            // 15) If two people share an office, they sould work together
            if(!worksWith.contains(new SymmetricPair(p1, p2))) {
                score -= 3;
            }
            
            // 14) People prefer to have their own offices 
            if(p2 != null) {
                score -= 4;
            }
            
            // 13) if a non-secretary hacker/non-hacker shares an office, 
            //     then he/she should share with another hacker/non-hacker
            if(p1.isHacker() && !p2.isHacker() && !p2.isSecretary()) {
                score -= 2;
            }
            if(p2.isHacker() && !p1.isHacker() && !p1.isSecretary()) {
                score -= 2;
            }
            
            // 11) A smoker shouldn't share an office with a non-smoker
            if( (p1.isSmoker() || p2.isSmoker()) && (!p1.isSmoker() || !p2.isSmoker()) ) {
                score -= 50;
            }
        }
        
        Iterator<Project> projectIter;
        projectIter = projects.values().iterator();
        
        while(projectIter.hasNext()) {
            Project project = projectIter.next();
            
            
        }
        
        return score;
    }
}
