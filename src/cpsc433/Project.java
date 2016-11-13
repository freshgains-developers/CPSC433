package cpsc433;

import java.util.HashMap;

/**
 * Created by thise_000 on 2016-11-09.
 */
public class Project extends Entity {

    private final HashMap personMap;
    private final HashMap headMap;
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
        headMap.put(p.getName(), p);
    }

    public HashMap getPersonMap() {
        return personMap;
    }

    public void setProjectPerson(Person p) {
        personMap.put(p.getName(), p);
    }

    public boolean checkPerson(String k) {
        return headMap.containsKey(k);
    }

    public void setLarge() {
        isLarge = true;
    }


















}
