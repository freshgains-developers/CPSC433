/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 *
 * @author Brenton Kruger
 * 
 * Class to hold group data
 */
public class Group extends Entity{
    
    private final ArrayList<Person> groupList;
    private final HashMap<String, Person> headMap;
    
    //constructor of group
    public Group(String name){
        super(name);
        //hashmap for people
        groupList = new ArrayList<Person>();
        headMap = new HashMap();
    }
    /**
     * 
     * @param p person to check
     * @return true if person is a member of group
     */
    public boolean memberOfGroup(Person p){
        String personKey = p.getName();
        return groupList.contains(p);
    }

    public HashMap<String, Person> getHeadMap (){
        return headMap;
    }

    public ArrayList<Person> getGroupList (){
        return groupList;
    }

    /**
     * @param p person to check
     * @return true if person is a head of the group
     */
    public boolean headOfGroup(Person p){
        String personKey = p.getName();
        return headMap.containsKey(personKey);        
    }
    
    /**add person to group
     * @param p is the key in the map
     * 
     */
    public void addToGroup(Person p){
        //check if person is in group && add to group if absent
        String personKey = p.getName();
        //if (!groupList.contains(personKey))
            groupList.add(p);
    }
    
    /**
     * remove person from group
     * @param p is a key in the group
     * 
     */
    public void remove(Person p){
        //check if a person is in the group and remove if present
        String personKey = p.getName();
        //if (groupList.contains(p))
        groupList.remove(p);
        if (headMap.containsKey(personKey)) //group heads must be a member of the group
            headMap.remove(personKey);
    }
    
    /**
     * add person as head of group
     * @param p person to add as a head
     * 
     */
    public void addAsHead(Person p){
        //check if person is in group
        //check if person is in head subgroup
        //add appropriately
        String personKey = p.getName();
        if (!groupList.contains(p)) //group heads must be a member of the group.
            groupList.add(p);
        if (!headMap.containsKey(personKey))
            headMap.put(personKey, p);
    }
    
    /**
     * removes a person as a head of a group, does not remove fully from group
     * @param p person to remove
     */
    public void removeAsHead(Person p){
        //check if person is in group
        //check if person is in head subgroup
        //remove appropriately
        String personKey = p.getName();
        if (headMap.containsKey(personKey))
            headMap.remove(personKey, p);
    }
}
