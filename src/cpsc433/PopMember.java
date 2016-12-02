/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import cpsc433.Room.RoomSize;
import cpsc433.Room.FullRoomException;
import java.util.*;
import cpsc433.Environment.UnsolvableInstanceException;

/**
 *
 * @author Brenton Kruger, Chris Kinzel
 * 
 * Container for a complete solution to the SisyphusI problem
 * 
 * Provides methods to evaluate and mutate population members
 *
 */
public class PopMember {
    private HashSet<SymmetricPair<Person, Person>> worksWith = null;
    private HashMap<String, Person> people = null;
    private HashMap<String, Group> groups = null;
    private HashMap<String, Project> projects = null;
    private HashMap<String, Room> rooms = null;
    private HashSet<SymmetricPair<Room, Room>> closeTo = null;
    private HashMap<Person, Room> assignments;


    /**
     * Construct population member
     * 
     * @param worksWith HashSet of works with relations
     * @param people HashMap of people
     * @param groups HashMap of groups
     * @param projects HashMap of projects
     * @param rooms HashMap of rooms
     * @param closeTo HashSet of close relations
     * @throws cpsc433.Environment.UnsolvableInstanceException thrown if solution is not possible without violating hard constraints
     * @throws cpsc433.Room.FullRoomException ** (shouldn't happen) ** thrown if initialization error occurs (this would be a bug)
     */
    public void PopMember(HashSet worksWith, HashMap people, HashMap groups, HashMap projects, HashMap rooms,HashSet closeTo) throws UnsolvableInstanceException,FullRoomException {
        //initialize population randomly.
        Random randGen = new Random();
        Iterator<Person> peopleIter = people.values().iterator();
        this.worksWith = worksWith;
        this.people = people;
        this.groups = groups;
        this.projects = projects;
        this.rooms = rooms;
        this.closeTo = closeTo;        
        
        if(people.size()<=2*rooms.size()){
        
            LinkedList<Person> managerQ = new LinkedList();
            LinkedList<Person> groupHeadQ = new LinkedList();
            LinkedList<Person> projectHeadQ = new LinkedList();
            LinkedList<Person> secretaryQ = new LinkedList();
            LinkedList<Person> personQ = new LinkedList();

            // identify all managers, group heads, project heads, secretaries 
            // and assign to proper queues
            while (peopleIter.hasNext()) {
                Person tempPerson = peopleIter.next();
                
                if (tempPerson.isManager()) {
                    managerQ.add(tempPerson);
                } else if (tempPerson.isGroupHead()) {
                    groupHeadQ.add(tempPerson);
                } else if (tempPerson.isProjectHead()) {
                    projectHeadQ.add(tempPerson);
                } else if (tempPerson.isSecretary()) {
                    secretaryQ.add(tempPerson);
                } else {
                    personQ.add(tempPerson);
                }
            }

            Room[] roomAddresses = (Room[]) rooms.values().toArray();
            int roomsLeft = rooms.size();

            //assign managers randomly.
            while (managerQ.peek() != null) {

                Person tempPerson = managerQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                tempRoom.putPerson(tempPerson);
                assignments.put(tempPerson, tempRoom);
                
                //update rooms left
                if (tempRoom.isFull()) {
                    roomsLeft--;
                    
                    //move last element to take the place of the full one
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; 
                }
            }
            //assign groupHeads randomly
            while (groupHeadQ.peek() != null) {

                Person tempPerson = groupHeadQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                tempRoom.putPerson(tempPerson);
                assignments.put(tempPerson, tempRoom);
                
                //update rooms left
                if (tempRoom.isFull()) {
                    roomsLeft--;
                    
                    //move last element to take the place of the full one
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; 
                }
            }

            //assign projectHeads randomly
            while (projectHeadQ.peek() != null) {

                Person tempPerson = projectHeadQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                tempRoom.putPerson(tempPerson);
                assignments.put(tempPerson, tempRoom);
                
                //update rooms left
                if (tempRoom.isFull()) {
                    roomsLeft--;
                    
                    //move last element to take the place of the full one
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; 
                }
            }

            //assign secretaries randomly
            while (secretaryQ.peek() != null) {

                Person tempPerson = secretaryQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                tempRoom.putPerson(tempPerson);
                assignments.put(tempPerson, tempRoom);
                
                //update rooms left
                if (tempRoom.isFull()) {
                    roomsLeft--;
                    
                    //move last element to take the place of the full one
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; 
                }
            }

            // assign everyone else randomly
            while (personQ.peek() != null) {

                Person tempPerson = personQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                tempRoom.putPerson(tempPerson);
                assignments.put(tempPerson, tempRoom);
                
                //update rooms left
                if (tempRoom.isFull()) {
                    roomsLeft--;
                    
                    //move last element to take the place of the full one
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; 
                }
            }
        } else {
            throw new Environment.UnsolvableInstanceException("Instance is unsolvable. No solution possible."); 
        }
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
