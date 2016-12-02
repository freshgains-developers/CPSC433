/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpsc433;
import java.util.HashMap;
/**
 *
 * @author Brenton Kruger
 * 
 * Class to hold group data
 */
public class Group extends Entity{
    
    private final HashMap<String ,Person> peopleList;
    private final HashMap<String , Person> managerList;
    private final HashMap<String, Person> secretaryList;
    private final HashMap<String, Person> headMap;
    
    //constructor of group
    public Group(String name){
        super(name);
        //hashmap for people
        peopleList = new HashMap<String, Person>();
        managerList = new HashMap<String, Person>();
        secretaryList = new HashMap<String, Person>();
        headMap = new HashMap();
    }


    /**
     * 
     * @param p person to check
     * @return true if person is a member of group
     */
    public boolean memberOfGroup(Person p){
        String personKey = p.getName();
        return peopleList.containsKey(personKey) && managerList.containsKey(personKey) && secretaryList.containsKey(personKey);
    }

    public HashMap<String, Person> getHeadMap (){
        return headMap;
    }

    public HashMap<String, Person> getSecretaryMap (){
        return secretaryList;
    }
    public HashMap<String, Person> getPersonMap (){
        return peopleList;
    }

    public HashMap<String, Person> getManagerMap (){
        return managerList;
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
        if (p.isManager()) {
            managerList.put(personKey, p);
            peopleList.remove(personKey);
        }

        if (p.isSecretary()) {
            secretaryList.put(personKey, p);
            peopleList.remove(personKey);
        }

        else {
            peopleList.put(personKey, p);
        }
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
        if (p.isManager()) {
            managerList.remove(personKey);
        }

        if (p.isSecretary()) {
            secretaryList.remove(personKey);
        }

        else {
            peopleList.remove(personKey);
        }

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
        if (!peopleList.containsKey(personKey) && !secretaryList.containsKey(personKey) && !managerList.containsKey(personKey) ) //group heads must be a member of the group.
        {
            if (p.isManager()) {
                managerList.remove(personKey);
            }

            if (p.isSecretary()) {
                secretaryList.remove(personKey);
            }

            else {
                peopleList.remove(personKey);
            }
        }
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
            headMap.remove(personKey);
    }
    
}
