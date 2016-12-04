/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import cpsc433.Room.RoomSize;
import cpsc433.Room.FullRoomException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
    private HashSet<SymmetricPair<Room, Room>> closeTo = null;
    private LinkedHashSet<Room> assignedRooms;
    
    private Room[] smallRooms;
    private Room[] mediumRooms;
    private Room[] largeRooms;


    /**
     * Construct population member
     * 
     * @param worksWith HashSet of works with relations
     * @param people HashMap of people
     * @param groups HashMap of groups
     * @param projects HashMap of projects
     * @param closeTo HashSet of close relations
     * @param managerQ LinkedList of managers
     * @param groupHeadQ LinkedList of groupHeads
     * @param projectHeadQ LinkedList of projectHeads
     * @param secretaryQ LinkedList of secretaries
     * @param personQ LinkedList of people
     * @param roomAddresses ArrayList of medium rooms
     * @param largeRoomAddresses ArrayList of large rooms
     * @param smallRoomAddresses ArrayList of small rooms
     * @throws cpsc433.Room.FullRoomException ** (shouldn't happen) ** thrown if initialization error occurs (this would be a bug)
     */
    public PopMember(HashSet worksWith, HashMap people, HashMap groups, HashMap projects, HashSet closeTo, LinkedList<Person> managerQ, LinkedList<Person> groupHeadQ, LinkedList<Person> projectHeadQ, LinkedList<Person> secretaryQ, LinkedList<Person> personQ, Room[] roomAddresses, Room[] largeRoomAddresses, Room[] smallRoomAddresses) throws FullRoomException {
        //initialize population randomly.
        this.worksWith = worksWith;
        this.people = people;
        this.groups = groups;
        this.projects = projects;
        this.closeTo = closeTo; 
        
        this.smallRooms = smallRoomAddresses;
        this.mediumRooms = roomAddresses;
        this.largeRooms = largeRoomAddresses;
        
        Random randGen = new Random();
        assignedRooms = new LinkedHashSet();

        
        int roomsLeft = roomAddresses.length;
        int largeRoomsLeft = largeRoomAddresses.length;
        int smallRoomsLeft = smallRoomAddresses.length;
        

        Iterator<Person> groupHeadIter = groupHeadQ.iterator();
        Iterator<Person> managerIter = managerQ.iterator();
        Iterator<Person> projectHeadIter = projectHeadQ.iterator();
        Iterator<Person> secretaryIter = secretaryQ.iterator();
        Iterator<Person> personIter = personQ.iterator();
        
        //assign groupHeads randomly
        while(groupHeadIter.hasNext()) {
            Person tempPerson = groupHeadIter.next();
            if (tempPerson.assignedRoom() == null){
                Room tempRoom;
                Room roomAssigned = null;
                int roomIndex;
                while (roomAssigned == null && (largeRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(largeRoomsLeft);
                    tempRoom = largeRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    }
                }
                
                while (roomAssigned == null && (smallRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(smallRoomsLeft);
                    tempRoom = smallRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    }
                }
                
                while (roomAssigned == null && (roomsLeft > 0)) {
                    roomIndex = randGen.nextInt(roomsLeft);
                    tempRoom = roomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    }
                }
            }
        }
        
        //assign managers randomly
        while(managerIter.hasNext()) {
            Person tempPerson = managerIter.next();
            if (tempPerson.assignedRoom() == null){
                Room tempRoom;
                Room roomAssigned = null;
                int roomIndex;
                
                while (roomAssigned == null && (smallRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(smallRoomsLeft);
                    tempRoom = smallRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    }
                } 
                
                while (roomAssigned == null && (largeRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(largeRoomsLeft);
                    tempRoom = largeRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    }
                }
                
                while (roomAssigned == null && (roomsLeft > 0)) {
                    roomIndex = randGen.nextInt(roomsLeft);
                    tempRoom = roomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    }
                }
            }
        }
        

        //assign projectHeads randomly
        while(projectHeadIter.hasNext()) {
            Person tempPerson = projectHeadIter.next();
            if (tempPerson.assignedRoom() == null){
                Room tempRoom;
                Room roomAssigned = null;
                int roomIndex;
                
                while (roomAssigned == null && (smallRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(smallRoomsLeft);
                    tempRoom = smallRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    }
                }
                
                while (roomAssigned == null && (largeRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(largeRoomsLeft);
                    tempRoom = largeRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    }
                }
                
                while (roomAssigned == null && (roomsLeft > 0)) {
                    roomIndex = randGen.nextInt(roomsLeft);
                    tempRoom = roomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        //move last element to take the place of the full one && update large rooms left
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    }
                }
            }
        }

        //assign secretaries randomly
        while(secretaryIter.hasNext()) {
            Person tempPerson = secretaryIter.next();
            if (tempPerson.assignedRoom() == null){
                Room tempRoom;
                Room roomAssigned = null;
                int roomIndex;
                while (roomAssigned == null && (largeRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(largeRoomsLeft);
                    tempRoom = largeRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        
                        if(roomAssigned.isFull()) {
                            //move last element to take the place of the full one && update large rooms left
                            largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft]; 
                        }
                    }
                }
 
                while (roomAssigned == null && (roomsLeft > 0)) {
                    roomIndex = randGen.nextInt(roomsLeft);
                    tempRoom = roomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        
                        if(roomAssigned.isFull()) {
                            //move last element to take the place of the full one && update large rooms left
                            roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                        }
                    }
                }
                
                while (roomAssigned == null && (smallRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(smallRoomsLeft);
                    tempRoom = smallRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        
                        if(roomAssigned.isFull()) {
                            //move last element to take the place of the full one && update large rooms left
                            smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                        }
                    }
                }
            }
        }

        // assign everyone else randomly
        while(personIter.hasNext()) {
            Person tempPerson = personIter.next();
            if (tempPerson.assignedRoom() == null){
                Room tempRoom;
                Room roomAssigned = null;
                int roomIndex;
                while (roomAssigned == null && (largeRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(largeRoomsLeft);
                    tempRoom = largeRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        if(roomAssigned.isFull()) {
                            //move last element to take the place of the full one && update large rooms left
                            largeRoomAddresses[roomIndex] = largeRoomAddresses[--largeRoomsLeft];
                        }
                    }
                }
                
                while (roomAssigned == null && (roomsLeft > 0)) {
                    roomIndex = randGen.nextInt(roomsLeft);
                    tempRoom = roomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        
                        if(roomAssigned.isFull()) {
                            //move last element to take the place of the full one && update large rooms left
                            roomAddresses[roomIndex] = roomAddresses[--roomsLeft];
                        }
                    }
                }
                
                while (roomAssigned == null && (smallRoomsLeft > 0)) {
                    roomIndex = randGen.nextInt(smallRoomsLeft);
                    tempRoom = smallRoomAddresses[roomIndex];
                    if (tempRoom.hasProod()) {
                        //remove room because it already has a prood in it
                        assignedRooms.add(tempRoom);
                        smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                    } else {
                        roomAssigned = tempRoom;
                        roomAssigned.putPerson(tempPerson);
                        assignedRooms.add(roomAssigned);
                        tempPerson.assignToRoom(roomAssigned);
                        
                        if(roomAssigned.isFull()) {
                            //move last element to take the place of the full one && update large rooms left
                            smallRoomAddresses[roomIndex] = smallRoomAddresses[--smallRoomsLeft];
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * Getter for rooms that have people in them
     * 
     * @return ArrayList of rooms with people assigned to them
     */
    public LinkedHashSet<Room> getAssignedRooms() {
        return assignedRooms;
    }
    
    public void mutate() throws FullRoomException {
        Random rand = new Random();
        int totalRooms = smallRooms.length + mediumRooms.length + largeRooms.length;
        
        for(int i=0;i<3;i++) {
            Room[] rooms = null;
            switch(i) {
                case 0:
                    rooms = smallRooms;
                    break;
                    
                case 1:
                    rooms = mediumRooms;
                    break;
                    
                case 2:
                    rooms = largeRooms;
                    break;
                
                default:
                    break;
            }
            
            for (Room room1 : rooms) {
                if (room1.hasProod()) {
                    Room room2;
                    int roomIndex = rand.nextInt(totalRooms);

                    if (roomIndex > smallRooms.length && roomIndex < mediumRooms.length + smallRooms.length) {
                        // Picked medium room
                        room2 = mediumRooms[roomIndex - smallRooms.length];
                    } else if (roomIndex > mediumRooms.length + smallRooms.length) {
                        // Picked large room
                        room2 = largeRooms[roomIndex - mediumRooms.length - smallRooms.length];
                    } else {
                        // Picked small room
                        room2 = smallRooms[roomIndex];
                    }

                    swapOccupants(room1, room2);
                }
            }
        }
    }
    
    private void swapOccupants(Room r1, Room r2) throws FullRoomException {
        Person p1 = r1.getAssignedPeople()[0];
        Person p2 = r1.getAssignedPeople()[1];
        
        r1.clearRoom();
        
        r1.putPerson(r2.getAssignedPeople()[0]);
        r1.putPerson(r2.getAssignedPeople()[1]);
        
        r2.clearRoom();
        
        r2.putPerson(p1);
        r2.putPerson(p2);
    }
    
    public void swapSingle (Room r1, Room r2) throws FullRoomException {
        Random x = new Random();
        int randInt = x.nextInt(2);
        int randInt2 = x.nextInt(2);
        Person[] room1 = r1.getAssignedPeople();
        Person[] room2 = r2.getAssignedPeople();

        Person a = room1[randInt];
        Person b = room2[randInt2];

        r1.removePerson(a);
        r2.removePerson(b);

        r1.putPerson(b);
        r2.putPerson(a);

    }





    /**
     * Evaluates and returns the score of a population member
     * 
     * @return sum of soft constraint penalties for this population member
     */
    public int score() {
        int score = 0;
        
        Iterator<Room> roomIter = assignedRooms.iterator();
        while(roomIter.hasNext()) { 
            Room r = roomIter.next();
            
            Person p1 = r.getAssignedPeople()[0];
            Person p2 = r.getAssignedPeople()[1];
            
            // Two people in the room
            if(p1 != null && p2 != null) {
                // 16) Two people shouldn't share a small room
                if(r.getSize() == RoomSize.SMALL) {
                    score -= 50; // * 2
                }
                
                // 15) If two people share an office, they sould work together
                if(!worksWith.contains(new SymmetricPair<>(p1, p2))) {
                    score -= 6; // * 2
                }
                
                // 13) if a non-secretary hacker/non-hacker shares an office, 
                //     then he/she should share with another hacker/non-hacker
                //
                //   If one is a hacker and the other is not and niether is
                //   a secretary -2 ?
                if ((p1.isHacker() != p2.isHacker()) && !p1.isSecretary() && !p2.isSecretary()) {
                    score -= 4; // * 2
                }
                
                // 11) A smoker shouldn't share an office with a non-smoker
                if (p1.isSmoker() != p2.isSmoker()) {
                    score -= 100; // * 2
                }
                
                // 4) secretaries should share offices with other secretaries
                if (p1.isSecretary() != p2.isSecretary()) {
                    score -= 10; // * 2
                }
                
                // 14) People prefer to have their own offices 
                score -= 8; // * 2
            } else if (p1 != null && p2 == null && p1.isSecretary()) {
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
            
            Iterator<Person> peopleIter;
            peopleIter = group.getPersonMap().values().iterator(); 
            
            Iterator<Person> secrIter;
            secrIter = group.getSecretaryMap().values().iterator();
            
            Iterator<Person> managerIter;
            managerIter = group.getManagerMap().values().iterator();
            
            while (headIter.hasNext()){ // head loop
                Person headValue = headIter.next();

                // 1) Group heads should have a large office
                if (headValue.assignedRoom().getSize() != RoomSize.LARGE){
                    score -= 40; 
                }
                               
                // 2.1) Group heads should be close to all members of their group
                while(peopleIter.hasNext()) {
                    Person person = peopleIter.next();
                    
                    if(!closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), person.assignedRoom()))) {
                        score -= 2;
                    }
                }
                
                // 2.2) Group heads should be close to all secretaries of their
                //      group
                boolean secretaryIsClose = false;
                while(secrIter.hasNext()) {
                    Person secretary = secrIter.next();
                    
                    if(!closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), secretary.assignedRoom()))) {
                        score -= 2;
                    } else {
                        secretaryIsClose = true;
                    }
                }
                
                // 3) Group heads should be located close to at least one
                //    secretary in the group
                if(!secretaryIsClose) {
                    score -= 30;
                }
                
                // 2.3) Group heads should be close to all managers of 
                //      their group 
                while(managerIter.hasNext()) {
                    Person manager = managerIter.next();
                    
                    if(!closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), manager.assignedRoom()))) {
                        score -= 2;
                        
                        // IMPLIED - 6) managers should be close to their 
                        //              group's head
                        score -= 20;
                    }
                    
                    peopleIter = group.getPersonMap().values().iterator(); 
                    while (peopleIter.hasNext()) {
                        Person person = peopleIter.next();
                        
                        // 7) Managers should be close to all members of 
                        //    their group
                        if (!closeTo.contains(new SymmetricPair<>(manager.assignedRoom(), person.assignedRoom()))) {
                            score -= 2;
                        }
                    }
                    
                    secretaryIsClose = false;
                    secrIter = group.getSecretaryMap().values().iterator();
                    while (secrIter.hasNext()) {
                        Person secretary = secrIter.next();

                        // 7) Managers should be close to all members of 
                        //    their group
                        if (!closeTo.contains(new SymmetricPair<>(manager.assignedRoom(), secretary.assignedRoom()))) {
                            score -= 2;
                        } else {
                            secretaryIsClose = true;
                        }
                    }
                    
                    // 5) managers should be close to at least one 
                    //    secretary in their group
                    if(!secretaryIsClose) {
                        score -= 20;
                    }
                }
                
              
            } // end head loop
            
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
                        score -= 14; // -7 * 2
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
                    }
                }
                
                if(project.getLarge()) {
                    for (Group group : headGroups) {
                        // 9) the heads of large projects should be close to
                        //    at least one secretary in their group
                        boolean secretaryIsClose = false;
                        
                        HashMap<String, Person> secretaries = group.getSecretaryMap();
                        Iterator<Person> secrIter = secretaries.values().iterator();
                        
                        while(secrIter.hasNext()) {
                            Person secretary = secrIter.next();
                            Room gmr = secretary.assignedRoom();
                            
                            if (closeTo.contains(new SymmetricPair<>(projectHeadRoom, gmr))) {
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
