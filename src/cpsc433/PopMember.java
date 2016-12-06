/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import cpsc433.Room.RoomSize;
import cpsc433.Room.FullRoomException;
import cpsc433.Swap.SwapType;
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
    private final LinkedHashSet<Room> assignedRooms;
    
    private final Room[] smallRooms;
    private final Room[] mediumRooms;
    private final Room[] largeRooms;
    
    private int smallRoomMutateCount;
    private int mediumRoomMutateCount;
    private int largeRoomMutateCount;
    
    private ArrayList<Swap> swapList;
    

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
        
        this.smallRoomMutateCount = smallRooms.length;
        this.mediumRoomMutateCount = mediumRooms.length;
        this.largeRoomMutateCount = largeRooms.length;
        
        this.swapList = new ArrayList<>();
        
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
                    if (tempRoom.hasProod() || tempRoom.isFull()) {
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
                    if (tempRoom.hasProod() || tempRoom.isFull()) {
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
                    if (tempRoom.hasProod() || tempRoom.isFull()) {
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
            } else {
                assignedRooms.add(tempPerson.assignedRoom());
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
                    if (tempRoom.hasProod() || tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
            } else {
                assignedRooms.add(tempPerson.assignedRoom());
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
            } else {
                assignedRooms.add(tempPerson.assignedRoom());
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
            } else {
                assignedRooms.add(tempPerson.assignedRoom());
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
                    if (tempRoom.hasProod()|| tempRoom.isFull()) {
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
            } else {
                assignedRooms.add(tempPerson.assignedRoom());
            }
        }
        
    }
    
    public LinkedHashSet<Room> copyAssignedRooms() {
        LinkedHashSet<Room> copy = new LinkedHashSet<>(assignedRooms.size());
        
        for(Room room : assignedRooms) {
            Room roomCopy = new Room(room);
            copy.add(roomCopy);
        }
        
        return copy;
    }
    
    public void printAssignments() {
        for(Person person : people.values()) {
            System.out.println("assign-to(" + person.getName() + ", " + person.assignedRoom().getName() + ")");
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
    
    private Room pickRandomSwapableRoom(Random rand) {
        Room room = null;
        while(room == null) {
            int totalRooms = smallRoomMutateCount + mediumRoomMutateCount + largeRoomMutateCount;

            // No rooms available for swap 
            if (totalRooms < 2) {
                return null;
            }

            int roomIndex = rand.nextInt(totalRooms);

            if (roomIndex >= smallRoomMutateCount && roomIndex < mediumRoomMutateCount + smallRoomMutateCount) {
                // Picked medium room
                roomIndex -= smallRoomMutateCount;
                room = mediumRooms[roomIndex];

                // If room has fixed assignments and a prood then we
                // can't do any swaps, swap with the back of the list
                if ((room.hasProod() && room.hasFixedAssignments()) || room.allFixed()) {
                    mediumRooms[roomIndex] = mediumRooms[--mediumRoomMutateCount];
                    mediumRooms[mediumRoomMutateCount] = room;

                    room = null;
                }
            } else if (roomIndex >= mediumRoomMutateCount + smallRoomMutateCount) {
                // Picked large room
                roomIndex -= mediumRoomMutateCount + smallRoomMutateCount;
                room = largeRooms[roomIndex];

                // If room has fixed assignments and a prood then we
                // can't do any swaps, swap with the back of the list
                if ((room.hasProod() && room.hasFixedAssignments()) || room.allFixed()) {
                    largeRooms[roomIndex] = largeRooms[--largeRoomMutateCount];
                    largeRooms[largeRoomMutateCount] = room;

                    room = null;
                }
            } else {
                // Picked small room
                room = smallRooms[roomIndex];

                // If room has fixed assignments and a prood then we
                // can't do any swaps, swap with the back of the list
                if ((room.hasProod() && room.hasFixedAssignments()) || room.allFixed()) {
                    smallRooms[roomIndex] = smallRooms[--smallRoomMutateCount];
                    smallRooms[smallRoomMutateCount] = room;

                    room = null;
                }
            }
        }
        
        return room;
    }
    
    public void mutate(int numSwaps) throws FullRoomException {
        this.swapList = new ArrayList<>();
        
        Random rand = new Random();
        
        while (numSwaps > 0) {
            Room room1 = pickRandomSwapableRoom(rand);
            Room room2 = pickRandomSwapableRoom(rand);
            
            // No rooms available 
            if(room1 == null || room2 == null) {
                break;
            }
            
            if(room1 == room2) {
                continue;
            }

            if (!room1.hasFixedAssignments() && !room2.hasFixedAssignments() && (room1.hasProod() || room2.hasProod() || rand.nextInt(2) == 0)) {
                swapOccupants(room1, room2, false);
            } else {
                swapSingle(room1, room2, rand);
            }

            if (room1.isEmpty()) {
                assignedRooms.remove(room1);
            } else {
                assignedRooms.add(room1);
            }
            if (room2.isEmpty()) {
                 assignedRooms.remove(room2);
            } else {
                assignedRooms.add(room2);
            }
            
            numSwaps--;
        }
    }
    
    public void rollback() throws FullRoomException {
        while(swapList.size() > 0) {
            Swap swap = swapList.remove(swapList.size()-1);
            
            if(swap.type == SwapType.OCCUPANT) {
                OccupantSwap occupantSwap = (OccupantSwap)swap;
                swapOccupants(occupantSwap.room1, occupantSwap.room2, true);
            } else {
                SingleSwap singleSwap = (SingleSwap)swap;
                
                Room room1 = singleSwap.room1;
                Room room2 = singleSwap.room2;
                
                room1.removePerson(singleSwap.person1);
                room2.removePerson(singleSwap.person2);
                
                room1.putPerson(singleSwap.person2);
                room2.putPerson(singleSwap.person1);
                
                if(singleSwap.person1 != null) {
                    singleSwap.person1.assignToRoom(room2);
                }
                if(singleSwap.person2 != null) {
                    singleSwap.person2.assignToRoom(room1);
                }
            }
        }
    }
    
    private void swapOccupants(Room r1, Room r2, boolean reverse) throws FullRoomException {
        Person p1 = r1.getAssignedPeople()[0];
        Person p2 = r1.getAssignedPeople()[1];
        
        Person p3 = r2.getAssignedPeople()[0];
        Person p4 = r2.getAssignedPeople()[1];
        
        r1.clearRoom();
        r2.clearRoom();
        
        r2.putPerson(p1);
        r2.putPerson(p2);

        r1.putPerson(p3);
        r1.putPerson(p4);
        
        if(p1 != null) {
            p1.assignToRoom(r2);
        }
        if(p2 != null) {
            p2.assignToRoom(r2);
        }
        if(p3 != null) {
            p3.assignToRoom(r1);
        }
        if(p4 != null) {
            p4.assignToRoom(r1);
        }
        
        if(!reverse) {
           swapList.add(new OccupantSwap(r1, r2)); 
        }
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

        if(a != null) {
           a.assignToRoom(r2); 
        }
        if(b != null) {
           b.assignToRoom(r1);
        }
        
        swapList.add(new SingleSwap(r1, r2, b, a));
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
                    //System.out.println(p1.getName() + " and " + p2.getName() + " share a room. -25 * 2             CONSTRAINT 16 ");
                    score -= 50;// * 2
                }
                
                // 15) If two people share an office, they sould work together
                if(!worksWith.contains(new SymmetricPair<>(p1, p2))) {
                    score -= 6; // * 2
                    //System.out.println(p1.getName() + " and " + p2.getName() + " don't work together and share a room. -3 * 2             CONSTRAINT 15 ");
                }
                
                // 13) if a non-secretary hacker/non-hacker shares an office, 
                //     then he/she should share with another hacker/non-hacker
                //
                //   If one is a hacker and the other is not and neither is
                //   a secretary -2 ?
                if ((p1.isHacker() != p2.isHacker()) && !p1.isSecretary() && !p2.isSecretary()) {
                    score -= 4; // * 2
                    if (p1.isHacker()) {
                        //System.out.println(p1.getName() + " is a Hacker while " + p2.getName() + " is not, and neither is a secretary. -4             CONSTRAINT 13");
                    }
                    else{
                        //System.out.println(p2.getName() + " is a Hacker while " + p1.getName() + " is not, and neither is a secretary. -4             CONSTRAINT 13 ");
                    }
                }
                
                // 11) A smoker shouldn't share an office with a non-smoker
                if (p1.isSmoker() != p2.isSmoker()) {
                    score -= 100; // * 2
                    //System.out.println(p1.getName() + " and " + p2.getName() + " share a room and one is one is a smoker and one is a non-smoker. -100             CONSTRAINT 11");
                }
                
                // 4) secretaries should share offices with other secretaries
                if (p1.isSecretary() != p2.isSecretary()) {
                    score -= 10; // * 2
                    //System.out.println(p1.getName() + " and " + p2.getName() + " share a room and are not both secretaries. -10             CONSTRAINT 4 ");
                }
                
                // 14) People prefer to have their own offices 
                score -= 8; // * 2
                //System.out.println(p1.getName() + " and " + p2.getName() + " share a room (and prefer to have their own). -4 * 2             CONSTRAINT 14 ");
            } else if (p1 != null && p2 == null && p1.isSecretary()) {
                score -= 5;
                //System.out.println(p1.getName()+ " does not share a room with another secretary. (and is a secretary) -5             CONSTRAINT 4 ");
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
                    //System.out.println(headValue.getName()+ " is a group head and does not have a large office. -40             CONSTRAINT 1 ");
                }
                               
                // 2.1) Group heads should be close to all members of their group
                while(peopleIter.hasNext()) {
                    Person person = peopleIter.next();
                    
                    if(person == headValue) {
                        continue;
                    }
                    
                    if(!closeTo.contains(new SymmetricPair<>(headValue.assignedRoom(), person.assignedRoom()))) {
                        score -= 2;
                        //System.out.println(headValue.getName()+ " is a group head and is not by " + person.getName() + ". -2             CONSTRAINT 2.1 ");
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
                        //System.out.println(headValue.getName()+ " is a group head and is not close to " + secretary.getName() + " (who is in " + headValue.getName()+"'s group and is a secretary). -2             CONSTRAINT 2.2 ");
                    } else {
                        secretaryIsClose = true;
                    }
                }
                
                // 3) Group heads should be located close to at least one
                //    secretary in the group
                if(!secretaryIsClose) {
                    score -= 30;
                    //System.out.println(headValue.getName()+ " is a group head and is not close to any secretaries in their group. -30             CONSTRAINT 3 ");
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
                        //System.out.println(headValue.getName()+ " is a group head and is not close to " + manager.getName() + " (who is in " + headValue.getName()+"'s group and is a manager). -2             CONSTRAINT 2.3 ");
                        
                        // IMPLIED - 6) managers should be close to their 
                        //              group's head
                        score -= 20;
                        //System.out.println(manager.getName()+ " is a manager and is not close to their group head. -20             CONSTRAINT 6 ");
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
                            //System.out.println(manager.getName()+ " is a manager and is not close to " + person.getName() + " (who is in " + manager.getName()+"'s group and is a person). -2             CONSTRAINT 7 ");

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
                            //System.out.println(manager.getName()+ " is a manager and is not close to " + secretary.getName() + " (who is in " + manager.getName()+"'s group and is a secretary). -2             CONSTRAINT 7 ");
                        } else {
                            secretaryIsClose = true;
                        }
                    }
                    
                    // 5) managers should be close to at least one 
                    //    secretary in their group
                    if(!secretaryIsClose) {
                        score -= 20;
                        //System.out.println(manager.getName()+ " is a manager and is not close to all secretaries in their group. -20             CONSTRAINT 5 ");
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
                        //System.out.println(p1.getName()+ " and" + p2.getName() + " share an office and are on the same project. -7*2             CONSTRAINT 12 ");
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
                        //System.out.println(projectHead.getName()+ " is a project head and is not close to " + projectMember.getName() + ", who is in their project. -5             CONSTRAINT 8 ");
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
                            //System.out.println(projectHead.getName() + " is a large project head and is not close to any of the secretaries in the same project. -10             CONSTRAINT 9 ");
                        }

                         // 10) The heads of large projects should be close to the 
                        //     head of their group
                        Iterator<Person> groupHeadIter = group.getHeadMap().values().iterator();
                        while (groupHeadIter.hasNext()) {
                            Person groupHead = groupHeadIter.next();
                            Room rgh = groupHead.assignedRoom();

                            if (!closeTo.contains(new SymmetricPair<>(rgh, projectHeadRoom))) {
                                score -= 10;
                                //System.out.println(projectHead.getName() + " is a large project head and is not close to " + groupHead.getName() + ", who is in the same project. -10             CONSTRAINT 10 ");
                            }
                        }
                    }
                }
            }
        }
        
        return score;
    }
    
        
    
}
