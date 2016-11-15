package cpsc433;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by thise_000 on 2016-11-09.
 */
public class Project extends Entity {

    private final HashMap<String, Person> personMap;
    private final HashMap<String, Person> headMap;
    private boolean isLarge = false;

    public Project(String name) {
        super(name);
        personMap = new HashMap();
        headMap = new HashMap();
    }

    public Iterator<Person> getHeads(){
        return headMap.values().iterator();
    }
    
    public Iterator<Person> getMembers(){
        return personMap.values().iterator();
    }
    public HashMap getHeadMap() {
        return headMap;
    }

    public boolean checkHead(String k) {
        return headMap.containsKey(k);
    }

    public void setProjectHead(Person p) {
        if (!checkHead(p.getName())) {
            headMap.put(p.getName(), p);
        }
    }

    public HashMap getPersonMap() {
        return personMap;
    }

    public void setProjectPerson(Person p) {
        if (!checkPerson(p.getName())){
            personMap.put(p.getName(), p);
        }
    }

    public boolean checkPerson(String k) {
        return personMap.containsKey(k);
    }

    public void setLarge() {
        isLarge = true;
    }

    public boolean getLarge(){
        return isLarge;
    }
















}
