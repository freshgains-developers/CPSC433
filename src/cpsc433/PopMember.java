/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import cpsc433.Room.RoomSize;
import cpsc433.Room.FullRoomException;
import cpsc433.Environment.UnsolvableInstanceException;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

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
            
            // Two people in the room
            if(p1 != null && p2 != null) {
                // 16) Two people shouldn't share a small room
                if(r.getSize() == RoomSize.SMALL) {
                    score -= 25;
                }
                
                // 15) If two people share an office, they sould work together
                if(!worksWith.contains(new SymmetricPair<>(p1, p2))) {
                    score -= 3;
                }
                
                // 14) People prefer to have their own offices 
                score -= 4;
            }
            
            // 13) if a non-secretary hacker/non-hacker shares an office, 
            //     then he/she should share with another hacker/non-hacker
            //
            //   If one is a hacker and the other is not and niether is
            //   a secretary -2 ?
            if( (p1.isHacker() != p2.isHacker()) && !p1.isSecretary() && !p2.isSecretary()) {
                score -= 2;
            }
            
            // 11) A smoker shouldn't share an office with a non-smoker
            if( p1.isSmoker() != p2.isSmoker() ) {
                score -= 50;
            }
            
            // 4) secretaries should share offices with other secretaries
            if( p1.isSecretary() != p2.isSecretary() ) {
                score -= 5;
            }
        }


        Iterator<Group> groupIter;
        groupIter = groups.values().iterator();

        while(groupIter.hasNext()) { // group loop

            Group group = groupIter.next();
            HashMap<String, Person> headMap =  group.getHeadMap();
            Iterator<Person> headIter;
            headIter = headMap.values().iterator();
            ArrayList<Person> peopleList = group.getGroupList();
           
            
            while (headIter.hasNext()){ // head loop
                Person headValue = headIter.next();

                // 1) Group heads should have a large office
                if (headValue.assignedRoom().getSize() != RoomSize.LARGE){
                    score -= 40; 
                }
               
                boolean allGroupMembersClose = true;
                boolean secretaryIsClose = false;
                
                for(Person person : peopleList) {
                    boolean personIsClose = closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), person.assignedRoom()));
                    
                    // 2) group heads should be close to all members of their group
                    if(!personIsClose && allGroupMembersClose) {
                        allGroupMembersClose = false;
                        score -= 2;
                    }
                    
                    // 6) Managers should be close to their group's head
                    if(person.isManager()) {
                        if(!personIsClose) {
                            score -= 20;

                            // IMPLIED - 7) managers should be close to all members 
                            //              of their group
                            person.isCloseToGroupMembers = false;
                            score -= -2;
                        }
                        
                        person.isCloseToGroupMembers = true;
                    } else if(person.isSecretary() && personIsClose) {
                        secretaryIsClose = true;
                        
                        // This break is allowed since at this point all managers
                        // have been processed (due to sorting) so if constraint
                        // 2 has already been violated we can break
                        if(!allGroupMembersClose) {
                            break;
                        }
                    } else if(!allGroupMembersClose && !person.isManager() && !person.isSecretary()) {
                        // 3) group heads should be located close to at least
                        //    one secretary in the group
                        if(!secretaryIsClose) {
                            score -= 30;
                        }
                        
                        // At this point both managers and secretaries have been
                        // processed so if allGroupMembersClose is false then
                        // constraint 2 does not need further evaluation and the
                        // loop can terminate
                        break;
                    }
                }
              
            } // end head loop
            
            // Manager loop
            int secretaryStartIndex = -1;
            for(int i=0; i < peopleList.size(); i++) {
                Person p1 = peopleList.get(i);
                if(!p1.isManager()) {
                    break;
                }
                
                // Haven't found secretary start index yet
                boolean skipManagers = false;
                if(secretaryStartIndex == -1) {
                    // Compare to other managers
                    for (int j = i + 1; j < peopleList.size(); j++) {
                        Person p2 = peopleList.get(j);
                        if (!p2.isManager()) {
                            secretaryStartIndex = j;
                            break;
                        }

                        if (p1.isCloseToGroupMembers) {
                            boolean personIsClose = closeTo.contains(new SymmetricPair<>(p1.assignedRoom(), p2.assignedRoom()));
                            if (!personIsClose) {
                                // 7) managers should be close to all members of
                                //    their group
                                p1.isCloseToGroupMembers = false;
                                p2.isCloseToGroupMembers = false;
                                score -= 2*2;
                            }
                        }
                    }
                    
                    skipManagers = true;
                }
                
                if(p1.isCloseToGroupMembers) {
                    // Loop through all group members
                    boolean secretaryIsClose = false;
                    for (int j = (skipManagers) ? secretaryStartIndex : (i + 1); j < peopleList.size(); j++) {
                        Person p2 = peopleList.get(j);
                        
                        boolean personIsClose = closeTo.contains(new SymmetricPair<>(p1.assignedRoom(), p2.assignedRoom()));
                        if(p2.isSecretary() && personIsClose) {
                            secretaryIsClose = true;
                            
                            // ** Ideally if this break fails we would skip to 
                            // normal people
                            if(!p1.isCloseToGroupMembers) {
                                break;
                            }
                        } else if(!p2.isManager() && !personIsClose) {
                            if(p1.isCloseToGroupMembers) {
                                // 7) managers should be close to all members of
                                //    their group
                                score -= 2;
                            
                                p1.isCloseToGroupMembers = false;
                            }
                            
                            if(!p2.isSecretary()) {
                                break;
                            }
                        } else if(p2.isManager() && !personIsClose) {
                            // 7) managers should be close to all members of
                            //    their group
                            score -= (p2.isCloseToGroupMembers) ? 4 : 2;
                            
                            p1.isCloseToGroupMembers = false;
                            p2.isCloseToGroupMembers = false;
                            
                            // Skip to secretaries
                            j = secretaryStartIndex-1;
                        }
                    }
                    
                    if(!secretaryIsClose) {
                        // 5) managers should be close to at least one
                        //    secretary in their group
                        score -= 20;
                    }
                } else {
                    // Loop through secretaries (7 is already implied so don't
                    // need to check for that)
                    for(int j = secretaryStartIndex; j < peopleList.size(); j++) {
                        Person secretary = peopleList.get(j);
                        if(!secretary.isSecretary()) {
                            // 5) managers should be close to at least one
                            //    secretary in their group
                            score -= 20;
                            
                            break;
                        }
                        
                        if(closeTo.contains(new SymmetricPair<>(p1.assignedRoom(), secretary.assignedRoom()))) {
                            break;
                        }
                    }
                }
            }
            
        } // end group loop

        Iterator<Project> projectIter;
        projectIter = projects.values().iterator();
        
        while(projectIter.hasNext()) {
            Project project = projectIter.next();
            ArrayList<Person> projectMembers = project.getPersonList();
            
            // 12) members of the same project should not share
            //     an office 
            for(int i = 0; i < projectMembers.size(); i++) {
                Person p1 = projectMembers.get(i);
                
                for(int j = i+1; j < projectMembers.size(); j++) {
                    Person p2 = projectMembers.get(j);
                    
                    if(p1.assignedRoom() == p2.assignedRoom() && p1 != p2) {
                        score -= 7;
                    }
                }
            }
            
            Iterator<Person> projectHeadIter;
            projectHeadIter = project.getHeadMap().values().iterator();

            while (projectHeadIter.hasNext()) {
                Person projectHead = projectHeadIter.next();
                ArrayList<Group> headGroups = projectHead.getGroups();
                
                Room projectHeadRoom = projectHead.assignedRoom();
                
                // 8) the heads of projects should be close to all 
                //    members of their project
                for(Person projectMember : projectMembers) {
                    if(projectMember == projectHead) {
                        continue;
                    }
                    
                    Room pmr = projectMember.assignedRoom();
                    
                    if(!closeTo.contains(new SymmetricPair<>(projectHeadRoom, pmr))) {
                        score -= -5;
                        break;
                    }
                }
                
                if(project.getLarge()) {
                    for (Group group : headGroups) {
                        // 9) the heads of large projects should be close to
                        //    at least one secretary in their group
                        boolean secretaryIsClose = false;
                        ArrayList<Person> groupMembers = group.getGroupList();
                        
                        // This loop runs O(num_secretaries + num_managers) 
                        // since the groupList is sorted 
                        for (Person groupMember : groupMembers) {
                            Room gmr = groupMember.assignedRoom();
                            
                            if (groupMember.isSecretary() && closeTo.contains(new SymmetricPair<>(projectHeadRoom, gmr))) {
                                secretaryIsClose = true;
                                break;
                            }
                        }

                        if (!secretaryIsClose) {
                            score -= 10;
                        }

                         // 10) The heads of large projects should be close to the 
                        //     head of their group
                        Iterator<Person> groupHeadIter = group.getHeadMap().values().iterator();
                        while (groupHeadIter.hasNext()) {
                            Person groupHead = groupHeadIter.next();
                            Room rgh = groupHead.assignedRoom();

                            if (!closeTo.contains(new SymmetricPair<>(rgh, projectHeadRoom))) {
                                score -= 10;
                            }
                        }
                    }
                }
            }
        }
        
        return score;
    }
    
        
    
}
