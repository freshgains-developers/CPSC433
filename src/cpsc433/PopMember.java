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
    
    private static int smallRoomMutateCount;
    private static int mediumRoomMutateCount;
    private static int largeRoomMutateCount;


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
        
        smallRoomMutateCount = smallRooms.length;
        mediumRoomMutateCount = mediumRooms.length;
        largeRoomMutateCount = largeRooms.length;
        
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
        int totalRooms = smallRoomMutateCount + mediumRoomMutateCount + largeRoomMutateCount;
        
        // This handles this case where 1 room 1-2 people causes an infinite loop
        if(totalRooms == 1) {
            return;
        }
        
        for(int i=0;i<3;i++) {
            Room[] rooms = null;
            int roomsLength = 0;
            
            switch(i) {
                case 0:
                    rooms = smallRooms;
                    roomsLength = smallRoomMutateCount;
                    break;
                    
                case 1:
                    rooms = mediumRooms;
                    roomsLength = mediumRoomMutateCount;
                    break;
                    
                case 2:
                    rooms = largeRooms;
                    roomsLength = largeRoomMutateCount;
                    break;
                
                default:
                    break;
            }
            
            for (int ri1 = 0; ri1 < roomsLength; ri1++) {
                Room room1 = rooms[ri1];
                
                // If room1 has fixed assignments and a prood then we
                // can't do any swaps, move it to the back
                if( (room1.hasProod() && room1.hasFixedAssignments()) || room1.allFixed()) {
                    rooms[ri1] = rooms[roomsLength-1];
                    rooms[roomsLength-1] = room1;
                    
                    totalRooms--;
                    
                    switch(i) {
                        case 0:
                            smallRoomMutateCount--;
                            break;
                            
                        case 1:
                            mediumRoomMutateCount--;
                            break;
                            
                        case 2:
                            largeRoomMutateCount--;
                            break;
                            
                        default:
                            break;
                    }
                    
                    ri1--;
                    continue;
                }
                
                Room room2 = null;
                do {
                    // No rooms available for swap mutate not possible
                    if(totalRooms == 0) {
                        return;
                    }
                    
                    int roomIndex = rand.nextInt(totalRooms);

                    if (roomIndex >= smallRoomMutateCount && roomIndex < mediumRoomMutateCount + smallRoomMutateCount) {
                        // Picked medium room
                        roomIndex -= smallRoomMutateCount;
                        room2 = mediumRooms[roomIndex];
                        
                        // If room2 has fixed assignments and a prood then we
                        // can't do any swaps, swap with the back of the list
                        if ( (room2.hasProod() && room2.hasFixedAssignments()) || room2.allFixed()) {
                            mediumRooms[roomIndex] = mediumRooms[--mediumRoomMutateCount];
                            mediumRooms[mediumRoomMutateCount] = room2;

                            room2 = mediumRooms[roomIndex];
                            
                            totalRooms--;
                            if(mediumRoomMutateCount == 0) {
                                // Re-select
                                room2 = room1;
                            }
                        }
                    } else if (roomIndex >= mediumRoomMutateCount + smallRoomMutateCount) {
                        // Picked large room
                        roomIndex -= mediumRoomMutateCount + smallRoomMutateCount;
                        room2 = largeRooms[roomIndex];
                        
                        // If room2 has fixed assignments and a prood then we
                        // can't do any swaps, swap with the back of the list
                        if ( (room2.hasProod() && room2.hasFixedAssignments()) || room2.allFixed()) {
                            largeRooms[roomIndex] = largeRooms[--largeRoomMutateCount];
                            largeRooms[largeRoomMutateCount] = room2;

                            room2 = largeRooms[roomIndex];
                            
                            totalRooms--;
                            if(largeRoomMutateCount == 0) {
                                // Re-select
                                room2 = room1;
                            }
                        }
                    } else {
                        // Picked small room
                        room2 = smallRooms[roomIndex];
                        
                        // If room2 has fixed assignments and a prood then we
                        // can't do any swaps, swap with the back of the list
                        if ( (room2.hasProod() && room2.hasFixedAssignments()) || room2.allFixed()) {
                            smallRooms[roomIndex] = smallRooms[--smallRoomMutateCount];
                            smallRooms[smallRoomMutateCount] = room2;

                            room2 = smallRooms[roomIndex];
                            
                            totalRooms--;
                            if(smallRoomMutateCount == 0) {
                                // Re-select
                                room2 = room1;
                            }
                        }
                    }
                    
                } while (room1 == room2);
 
                
                if(!room1.hasFixedAssignments() && !room2.hasFixedAssignments() && (room1.hasProod() || room2.hasProod() || rand.nextInt(2) == 0)) {
                    swapOccupants(room1, room2);
                } else {
                    swapSingle(room1, room2, rand);
                }


                if (room1.isEmpty()){
                    assignedRooms.remove(room1);
                } else {
                    assignedRooms.add(room1);
                }
                if (room2.isEmpty()){
                    assignedRooms.remove(room2);
                } else {
                    assignedRooms.add(room2);
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
    
    private void swapSingle (Room r1, Room r2, Random rand) throws FullRoomException {
        int randInt = rand.nextInt(2);
        int randInt2 = rand.nextInt(2);
        Person[] room1 = r1.getAssignedPeople();
        Person[] room2 = r2.getAssignedPeople();

        Person a = room1[randInt];
        Person b = room2[randInt2];


        if (a != null && a.getFixed()){
            a = room1[1-randInt];
        }

        if (b != null && b.getFixed()){
            b = room2[1-randInt2];
        }

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
                    score -= 50;// * 2
                }
                
                // 15) If two people share an office, they sould work together
                if(!worksWith.contains(new SymmetricPair<>(p1, p2))) {
                    score -= 6; // * 2
                }
                
                // 13) if a non-secretary hacker/non-hacker shares an office, 
                //     then he/she should share with another hacker/non-hacker
                //
                //   If one is a hacker and the other is not and neither is
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
                    
                    if(person == headValue) {
                        continue;
                    }
                    
                    if(!closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), person.assignedRoom()))) {
                        score -= 2;
                    }
                }
                
                // 2.2) Group heads should be close to all secretaries of their
                //      group
                boolean secretaryIsClose = false;
                while(secrIter.hasNext()) {
                    Person secretary = secrIter.next();
                    
                    if(headValue == secretary) {
                        continue;
                    }
                    
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
                    
                    if(manager == headValue) {
                        continue;
                    }
                    
                    if(!closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), manager.assignedRoom()))) {
                        score -= 2;
                        
                        // IMPLIED - 6) managers should be close to their 
                        //              group's head
                        score -= 20;
                    }
                    
                    peopleIter = group.getPersonMap().values().iterator(); 
                    while (peopleIter.hasNext()) {
                        Person person = peopleIter.next();
                        
                        if(person == manager) {
                            continue;
                        }
                        
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
                        
                        if(secretary == manager) {
                            continue;
                        }

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
