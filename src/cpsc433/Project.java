package cpsc433;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thise_000 on 2016-11-09.
 */
public class Project extends Entity {

    private ArrayList<Person> personList;
    private ArrayList<Person> headList;
    private int size;
    private boolean isLarge;

    public Project(String name) {
        super(name);
        personList = new ArrayList<Person>();
        headList = new ArrayList<Person>();
    }

    public ArrayList<Person> getHead() {
        return headList;
    }














}
