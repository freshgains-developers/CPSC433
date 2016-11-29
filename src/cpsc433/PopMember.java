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
import java.util.Queue;

/**
 *
 * @author Brenton
 * Container for a complete solution
 * hashmap <person, room> assignments
 */
public class PopMember{
    private HashSet<SymmetricPair<Person, Person>> worksWith = null;
    private HashMap<String, Person> people = null;
    private HashMap<String, Group> groups = null;
    private HashMap<String, Project> projects = null;
    private HashMap<String, Room> rooms = null;
    private HashSet<SymmetricPair<Room, Room>> closeTo = null;
    private HashMap<Person, Room> assignments;
    private Queue

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

        Queue managerQ = new Queue();
        Queue groupHeadQ = new Queue();
        Queue projectHeadQ = new Queue();
        Queue secretaryQ = new Queue();
        Queue plebQ = new Queue();




        while peopleIter.hasNext(){
            Person tempPerson = peopleIter.next()
            switch (tempPerson){
                case tempPerson.isManager(): managerQ.add(tempPerson);
                    break;
                case tempPerson.isSecretary(): secretaryQ.add(tempPerson);
                    break;
                default: plebQ.add(tempPerson)
                    break;
            }

        }


        //managers first
        //group heads after
        //project heads
        //secretaries
        //plebs

        //for all managers, assign random rooms
        //for all groups, get group heads && assign random rooms
        //for all project heads, assign random rooms
        //for all secretaries, assign rooms
        //for all plebs, assign rooms
    }
}
