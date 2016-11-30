/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.ArrayList;
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
        HashMap<Person, Room> assignments = new Hashmap();
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
        //random room selection implemented with an array and an int
        int roomsLeft = rooms.size();
        Array roomAddresses = rooms..values().toArray();

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
    private Room getRandomRoom(){
    
    return randRoom;
    }
        
    
}
