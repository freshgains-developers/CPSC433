package cpsc433;

import java.util.HashMap;

/**
 * Created by thise_000 on 2016-11-09.
 */
public class Project extends Entity {

    private HashMap personMap;
    private HashMap headMap;
    private boolean isLarge = false;

    public Project(String name) {
        super(name);
        personMap = new HashMap();
        headMap = new HashMap();
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
        if (!checkHead(p.getName())){
        personMap.put(p.getName(), p);
    }
    }

    public boolean checkPerson(String k) {
        return headMap.containsKey(k);
    }

    public void setLarge() {
        isLarge = true;
    }

    public boolean getLarge(){
        return isLarge;
    }
















}
