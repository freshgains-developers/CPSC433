package cpsc433;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by thise_000 on 2016-11-09.
 */
public class Project extends Entity {

    private final ArrayList<Person> personList;
    private final HashMap<String, Person> headMap;
    private boolean isLarge = false;

    public Project(String name) {
        super(name);
        personList = new ArrayList<>();
        headMap = new HashMap();
    }

    public Iterator<Person> getHeads(){
        return headMap.values().iterator();
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

    public ArrayList<Person> getPersonList() {
        return personList;
    }

    public void setProjectPerson(Person p) {
         personList.add(p);
    }

    public void setLarge() {
        isLarge = true;
    }

    public boolean getLarge(){
        return isLarge;
    }
}
