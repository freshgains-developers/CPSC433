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
    private boolean solutionPossible;


    @SuppressWarnings("empty-statement")
    public void PopMember(HashSet worksWith, HashMap people, HashMap groups, HashMap projects, HashMap rooms,HashSet closeTo) throws UnsolvableInstanceException{
        //initialize population randomly.
        Random randGen = new Random();
        Iterator<Person> peopleIter = people.values().iterator();
        this.worksWith = worksWith;
        this.people = people;
        this.groups = groups;
        this.projects = projects;
        this.rooms = rooms;
        this.closeTo = closeTo;
        solutionPossible = true;
        
        
        if(people.size()<=2*rooms.size()){
        
            LinkedList<Person> managerQ = new LinkedList();
            LinkedList<Person> groupHeadQ = new LinkedList();
            LinkedList<Person> projectHeadQ = new LinkedList();
            LinkedList<Person> secretaryQ = new LinkedList();
            LinkedList<Person> plebQ = new LinkedList();

            //identify all managers, group heads, project heads, secretaries and plebs and assign to proper queues
            while (peopleIter.hasNext()) {
                Person tempPerson = peopleIter.next();
                if (tempPerson.isManager()) {
                    managerQ.add(tempPerson);
                    break;
                } else if (tempPerson.isGroupHead()) {
                    groupHeadQ.add(tempPerson);
                    break;
                } else if (tempPerson.isProjectHead()) {
                    projectHeadQ.add(tempPerson);
                    break;
                } else if (tempPerson.isSecretary()) {
                    secretaryQ.add(tempPerson);
                    break;
                } else {
                    plebQ.add(tempPerson);
                    break;
                }
            }

            Room[] roomAddresses = (Room[]) rooms.values().toArray();
            int roomsLeft = rooms.size();

            //assign managers randomly.
            while (managerQ.peek() != null) {

                Person tempPerson = managerQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                try {
                    tempRoom.putPerson(tempPerson);
                } catch (FullRoomException e) {
                    ;
                }
                assignments.put(tempPerson, tempRoom);
                //update rooms left
                if (tempRoom.isFull()) {
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; //move last element to take the place of the full one
                    roomsLeft -= roomsLeft;
                }
            }
            //assign groupHeads randomly
            while (groupHeadQ.peek() != null) {

                Person tempPerson = groupHeadQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                try {
                    tempRoom.putPerson(tempPerson);
                } catch (FullRoomException e) {
                    ;
                }
                assignments.put(tempPerson, tempRoom);
                //update rooms left
                if (tempRoom.isFull()) {
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; //move last element to take the place of the full one
                    roomsLeft -= roomsLeft;
                }
            }

            //assign projectHeads randomly
            while (projectHeadQ.peek() != null) {

                Person tempPerson = projectHeadQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                try {
                    tempRoom.putPerson(tempPerson);
                } catch (FullRoomException e) {
                    ;
                }
                assignments.put(tempPerson, tempRoom);
                //update rooms left
                if (tempRoom.isFull()) {
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; //move last element to take the place of the full one
                    roomsLeft -= roomsLeft;
                }
            }

            //assign secretaries randomly
            while (secretaryQ.peek() != null) {

                Person tempPerson = secretaryQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                try {
                    tempRoom.putPerson(tempPerson);
                } catch (FullRoomException e) {
                    ;
                }
                assignments.put(tempPerson, tempRoom);
                //update rooms left
                if (tempRoom.isFull()) {
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; //move last element to take the place of the full one
                    roomsLeft -= roomsLeft;
                }
            }

            //assign plebs randomly
            while (plebQ.peek() != null) {

                Person tempPerson = plebQ.remove();
                int roomIndex = randGen.nextInt(roomsLeft);
                Room tempRoom = roomAddresses[roomIndex];

                try {
                    tempRoom.putPerson(tempPerson);
                } catch (FullRoomException e) {
                    ;
                }
                assignments.put(tempPerson, tempRoom);
                //update rooms left
                if (tempRoom.isFull()) {
                    roomAddresses[roomIndex] = roomAddresses[roomsLeft]; //move last element to take the place of the full one
                    roomsLeft -= roomsLeft;
                }
            }
        }
        else
            throw new Environment.UnsolvableInstanceException("Instance is unsolvable. No solution possible."); //send this sucker to hell
        
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
