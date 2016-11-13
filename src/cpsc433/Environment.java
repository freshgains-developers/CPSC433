/**
 *
 */
package cpsc433;

import cpsc433.Predicate.ParamType;
import cpsc433.Room.RoomSize;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * This is class extends {@link cpsc433.PredicateReader} just as required to 
 * in the assignment. You can extend this class to include your predicate definitions
 * or you can create another class that extends {@link cpsc433.PredicateReader} and
 * use that one.
 * <p>
 * I have defined this class as a singleton.
 *
 * <p>Copyright: Copyright (c) 2003-16, Department of Computer Science, University 
 * of Calgary.  Permission to use, copy, modify, distribute and sell this 
 * software and its documentation for any purpose is hereby granted without 
 * fee, provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in supporting 
 * documentation.  The Department of Computer Science makes no representations
 * about the suitability of this software for any purpose.  It is provided
 * "as is" without express or implied warranty.</p>
 *
 * @author <a href="http://www.cpsc.ucalgary.ca/~kremer/">Rob Kremer</a>
 *
 */
public class Environment extends PredicateReader implements SisyphusPredicates {

	private static Environment instance=null;
	protected boolean fixedAssignments=false;

        // TODO: Maybe use LinkedHashMap instead ?
        private HashMap<String, Person> people = null;
        private HashSet<SymmetricPair<Person, Person>> worksWith = null;

        private HashMap<String, Group> groups = null;
        private HashMap<String, Project> projects = null;

        private HashMap<String, Room> rooms = null;
        private HashSet<SymmetricPair<Room, Room>> closeTo = null;

	protected Environment(String name) {
		super(name==null?"theEnvironment":name);

                worksWith = new HashSet();
                people = new HashMap();
                groups = new HashMap();
                projects = new HashMap();
                rooms = new HashMap();
                closeTo = new HashSet();
	}

	/**
	 * A getter for the global instance of this class.  If an instance of this class
	 * does not already exist, it will be created.
	 * @return The singleton (global) instance.
	 */
	public static Environment get() {
		if (instance==null) instance = new Environment(null);
		return instance;
	}

        @Override
	public void a_person(String p) {
            // Check to see if there is a person of name p.
            // If there is then do nothing
            if(people.containsKey(p)){
                // Do nothing (Because there is already a person with that name)
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                people.put(p, newPerson);
            }
        }

        @Override
	public boolean e_person(String p) {
            //Checks if a person exists with the name p
            return people.containsKey(p);
        }

        @Override
	public void a_secretary(String p) {
            // Check to see if there is a person of name p.
            // If there is then grab the person and make them a secretary.
            if(people.containsKey(p)){
                Person personWithNameP;
                personWithNameP = people.get(p);
                personWithNameP.setSecretary(true);
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                newPerson.setSecretary(true);
                people.put(p, newPerson);
            }
        }
        @Override
	public boolean e_secretary(String p) {
            // If the person exists and is a secretary then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isSecretary()){
                    return true;
                }
            }
            // Else return false
            return false;
        }

        @Override
	public void a_researcher(String p) {
            // Check to see if there is a person of name p.
            // If there is then grab the person and make them a researcher.
            if(people.containsKey(p)){
                Person personWithNameP;
                personWithNameP = people.get(p);
                personWithNameP.setResearcher(true);
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                newPerson.setResearcher(true);
                people.put(p, newPerson);
            }
        }
        @Override
	public boolean e_researcher(String p) {
            // If the person exists and is a researcher then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isResearcher()){
                    return true;
                }
            }
            // Else return false
            return false;
        }


        @Override
	public void a_manager(String p) {
            // Check to see if there is a person of name p.
            // If there is then grab the person and make them a manager.
            if(people.containsKey(p)){
                Person personWithNameP;
                personWithNameP = people.get(p);
                personWithNameP.setManager(true);
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                newPerson.setManager(true);
                people.put(p, newPerson);
            }
        }
        @Override
	public boolean e_manager(String p) {
            // If the person exists and is a manager then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isManager()){
                    return true;
                }
            }
            // Else return false
            return false;
        }

        @Override
	public void a_smoker(String p) {
            // Check to see if there is a person of name p.
            // If there is then grab the person and make them a smoker.
            if(people.containsKey(p)){
                Person personWithNameP;
                personWithNameP = people.get(p);
                personWithNameP.setSmoker(true);
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                newPerson.setSmoker(true);
                people.put(p, newPerson);
            }
        }
        @Override
	public boolean e_smoker(String p) {
            // If the person exists and is a smoker then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isSmoker()){
                    return true;
                }
            }
            // Else return false
            return false;
        }

        @Override
	public void a_hacker(String p) {
            // Check to see if there is a person of name p.
            // If there is then grab the person and make them a hacker.
            if(people.containsKey(p)){
                Person personWithNameP;
                personWithNameP = people.get(p);
                personWithNameP.setHacker(true);
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                newPerson.setHacker(true);
                people.put(p, newPerson);
            }
        }
        @Override
	public boolean e_hacker(String p) {
            // If the person exists and is a hacker then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isHacker()){
                    return true;
                }
            }
            // Else return false
            return false;
        }

        @Override
	public void a_group(String p, String grp) {

        }
        @Override
	public boolean e_group(String p, String grp) {
            return false;
        }

        @Override
	public void a_project(String p, String prj) {
            Project project;
            Person person;
            if (projects.containsKey(prj)){
                project = projects.get(prj);
            }
            else {
                project = new Project(prj);
                projects.put(prj,project);
            }

            if (people.containsKey(p)){
                person = people.get(p);
                project.setProjectPerson(person);
                person.addProject(project);
            }
            else{
                person = new Person(p);
                project.setProjectHead(person);
                person.addProject(project);
            }
        }
        @Override
	public boolean e_project(String p, String prj) {
            if (people.containsKey(p) && projects.containsKey(prj)) {
                Person person = people.get(p);
                Project project = projects.get(prj);
                return project.checkPerson(p);
            }
            return false;
        }

        @Override
	public void a_heads_group(String p, String grp) {

        }
        @Override
	public boolean e_heads_group(String p, String grp) {
            return false;
        }

        @Override
	public void a_heads_project(String p, String prj) {
            a_project(p,prj);
            Person person = people.get(p);
            projects.get(prj).setProjectHead(person);
        }
        @Override
	public boolean e_heads_project(String p, String prj) {
            if (e_project(p,prj) && projects.get(prj).checkHead(p)) {
                return true;
            }
            return false;
        }

        @Override
	public void a_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s) {

        }
        @Override
	public boolean e_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s) {
            return false;
        }

        @Override
	public void a_works_with(String p, String p2) {

        }
        @Override
	public boolean e_works_with(String p, String p2) {
            return false;
        }

        @Override
	public void a_assign_to(String p, String room) throws Exception {
            Person person;
            Room   roomObj;

            if(people.containsKey(p)) {
                person = people.get(p);
            } else {
                person = new Person(p);
                people.put(p, person);
            }

            if(rooms.containsKey(room)) {
                roomObj = rooms.get(room);
            } else {
                roomObj = new Room(room, RoomSize.MEDIUM);
                rooms.put(room, roomObj);
            }

            person.assignToRoom(roomObj);
            roomObj.putPerson(person);

            // TODO: possibly throw exception if room is full or person is assigned twice ? ^^^^
        }
        @Override
	public boolean e_assign_to(String p, String room) {
            // Check if the person exists
            if(!people.containsKey(p)) {
                // Person doesn't exist
                return false;
            }

            // Check if the person is assigned to a room
            Person person = people.get(p);
            Room assignedRoom = person.assignedRoom();

            if(assignedRoom == null) {
                // Person not assigned a room
                return false;
            }

            // Check if the assigned room matches the query
            return assignedRoom.getName().equals(room);
        }

	// ROOMS
        @Override
	public void a_room(String r) {
            // Add room if it doesn't already exist
            if(!rooms.containsKey(r)) {
                rooms.put(r, new Room(r, RoomSize.MEDIUM));
            }
        }
        @Override
	public boolean e_room(String r) {
            return rooms.containsKey(r);
        }

        @Override
	public void a_close(String room, String room2) {
            Room roomObj1, roomObj2;

            // First check if the specified rooms exist, if not
            // create them

            if(!rooms.containsKey(room)) {
                roomObj1 = new Room(room, RoomSize.MEDIUM);
                rooms.put(room, roomObj1);
            } else {
                roomObj1 = rooms.get(room);
            }

            if(!rooms.containsKey(room2)) {
                roomObj2 = new Room(room2, RoomSize.MEDIUM);
                rooms.put(room2, roomObj2);
            } else {
                roomObj2 = rooms.get(room2);
            }

            // Create a new relation pair (room1, room2) and add
            // it to the set of relations
            //
            // Note that duplicate pairs are not added by defition
            // of the add method in HashSet including pairs of the
            // form (room2, room1)
            closeTo.add(new SymmetricPair(roomObj1, roomObj2));
        }
        @Override
	public boolean e_close(String room, String room2) {
            return ( rooms.containsKey(room) && rooms.containsKey(room2)
                     && closeTo.contains(new SymmetricPair(rooms.get(room), rooms.get(room2))) );
        }

        @Override
	public void a_close(String room, TreeSet<Pair<ParamType,Object>> set) {
            Room roomObj1;

            // If the room does not exist create it before
            // proceeding
            if(!rooms.containsKey(room)) {
                roomObj1 = new Room(room, RoomSize.MEDIUM);
                rooms.put(room, roomObj1);
            } else {
                roomObj1 = rooms.get(room);
            }

            // Iterate through all rooms in TreeSet, if any rooms
            // don't exist create them then add the close relation
            // (room1, room_i) to the closeTo set
            Iterator<Pair<ParamType,Object>> iter = set.iterator();
            while(iter.hasNext()) {
                Room room_i;
                String roomName_i = (String)iter.next().getValue();

                if(!rooms.containsKey(roomName_i)) {
                    room_i = new Room(roomName_i, RoomSize.MEDIUM);
                    rooms.put(roomName_i, room_i);
                } else {
                    room_i = rooms.get(roomName_i);
                }

                closeTo.add(new SymmetricPair(roomObj1, room_i));
            }
        }
        @Override
	public boolean e_close(String room, TreeSet<Pair<ParamType,Object>> set) {
            // If the first room name is not associated with any
            // known room or the set is empty return false
            if(!rooms.containsKey(room) || set.isEmpty()) {
                return false;
            }

            Room roomObj1 = rooms.get(room);
            Iterator<Pair<ParamType,Object>> iter = set.iterator();

            while(iter.hasNext()) {
                Room room_i;
                String roomName_i = (String)iter.next().getValue();

                if(!rooms.containsKey(roomName_i)) {
                    return false;
                } else {
                    room_i = rooms.get(roomName_i);
                }

                if( !closeTo.contains(new SymmetricPair(roomObj1, room_i)) ) {
                    return false;
                }
            }

            return true;
        }

        @Override
	public void a_large_room(String r) {
            if(!rooms.containsKey(r)) {
                // Add room if it doesn't already exist
                rooms.put(r, new Room(r, RoomSize.LARGE));
            } else {
                // If the room already exists update the size
                Room room = rooms.get(r);
                room.setSize(RoomSize.LARGE);
            }
        }
        @Override
	public boolean e_large_room(String r) {
            return (rooms.containsKey(r) && rooms.get(r).getSize() == RoomSize.LARGE);
        }

        @Override
	public void a_medium_room(String r) {
            if(!rooms.containsKey(r)) {
                // Add room if it doesn't already exist
                rooms.put(r, new Room(r, RoomSize.MEDIUM));
            } else {
                // If the room already exists update the size
                Room room = rooms.get(r);
                room.setSize(RoomSize.MEDIUM);
            }
        }
        @Override
	public boolean e_medium_room(String r) {
            return (rooms.containsKey(r) && rooms.get(r).getSize() == RoomSize.MEDIUM);
        }

        @Override
	public void a_small_room(String r) {
            if(!rooms.containsKey(r)) {
                // Add room if it doesn't already exist
                rooms.put(r, new Room(r, RoomSize.SMALL));
            } else {
                // If the room already exists update the size
                Room room = rooms.get(r);
                room.setSize(RoomSize.SMALL);
            }
        }
        @Override
	public boolean e_small_room(String r) {
            return (rooms.containsKey(r) && rooms.get(r).getSize() == RoomSize.SMALL);
        }

	// GROUPS
        @Override
	public void a_group(String g) {

        }
        @Override
	public boolean e_group(String g) {
            return false;
        }

	// PROJECTS
        @Override
	public void a_project(String p) {
            if (!projects.containsKey(p)){
                Project newProject = new Project(p);
                projects.put(p, newProject);
            }

        }
        @Override
	public boolean e_project(String p) {
            return groups.containsKey(p);
        }

        @Override
	public void a_large_project(String prj) {
            if (!projects.containsKey(prj)) {
                Project newProject = new Project(prj);
                newProject.setLarge();
                projects.put(prj, newProject);
            }

            else {
                Project project = projects.get(prj);
                project.setLarge();
            }

        }
        @Override
	public boolean e_large_project(String prj) {
            return projects.containsKey(prj) && projects.get(prj).getLarge();
        }

	/**
	 * The help text for the exit() predicate.
	 */
	public static String h_exit = "quit the program";
	/**
	 * The definition of the exit() assertion predicate.  It will exit the program abruptly.
	 */
	public void a_exit() {
            System.exit(0);
	}
}
