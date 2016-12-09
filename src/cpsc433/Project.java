package cpsc433;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Iterator;

/**
 *
 * The code for the set of projects
 * @author Chris Kinzel, Eric Ma, Brenton Kruger, Micheal Friesen
 *
 */
public class Project extends Entity {

    // headmap is the hashmap of all the heads in the project (unique/no repeats)
    private final ArrayList<Person> personList;
    private final HashMap<String, Person> headMap;
    private boolean isLarge = false;

    /**
     * Creates a new project with the name passed
     * @param name the name of the project
     */
    public Project(String name) {
        super(name);
        personList = new ArrayList();
        headMap = new HashMap();
    }

    /**
     * @return an iterator for the project heads
     */
    public Iterator<Person> getHeads() {
        return headMap.values().iterator();
    }

    /**
     *
     * @return returns the headMap
     */
    public HashMap getHeadMap() {
        return headMap;
    }

    /**
     * Checks if a proj Head with the key k exists
     * @param k name of the project head
     * @return boolean based on if it is true
     */
    public boolean checkHead(String k) {
        return headMap.containsKey(k);
    }


    /**
     * adds a project head to the project
     * @param p - the person we add
     */
    public void setProjectHead(Person p) {
        if (!checkHead(p.getName())) {
            headMap.put(p.getName(), p);
        }
    }

    /**
     * Getter for the list of people in the project
     * @return ArrayList of Persons that make up the project
     */
    public ArrayList<Person> getPersonList() {
        return personList;
    }

    /**
     * Adds a person to a project
     * @param p
     */
    public void setProjectPerson(Person p) {
        personList.add(p);
    }

    /**
     * Makes it a large project
     */
    public void setLarge() {
        isLarge = true;
    }

    /**
     * Checks if the project if large
     * @return boolean if the project is large or not
     */
    public boolean getLarge() {
        return isLarge;
    }
}
