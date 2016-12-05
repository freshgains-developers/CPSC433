/**
 *
 */
package cpsc433;

import cpsc433.Predicate.ParamType;
import static cpsc433.PredicateReader.error;
import cpsc433.Room.FullRoomException;
import cpsc433.Room.RoomSize;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
    
    public static class UnsolvableInstanceException extends Exception {
        public UnsolvableInstanceException() {
            super();
        }

        public UnsolvableInstanceException(String message) {
            super(message);
        }

        public UnsolvableInstanceException(String message, Throwable cause) {
            super(message, cause);
        }

        public UnsolvableInstanceException(Throwable cause) {
            super(cause);
        }
    }

	private static Environment instance=null;
	protected boolean fixedAssignments=false;

        // TODO: Maybe use LinkedHashMap instead ?
        public ArrayList<HashMap<String, Person>> people = null;
        private HashSet<SymmetricPair<Person, Person>> worksWith = null;

        private ArrayList<HashMap<String, Group>> groups = null;
        private ArrayList<HashMap<String, Project>> projects = null;

        public ArrayList<HashMap<String, Room>> rooms = null;
        public ArrayList<HashMap<String, Room>> largeRooms = null;
        public ArrayList<HashMap<String, Room>> smallRooms = null;
        private HashSet<SymmetricPair<Room, Room>> closeTo = null;

	protected Environment(String name, int popSize) {
		super(name==null?"theEnvironment":name);

                worksWith = new HashSet();
                people = new ArrayList<>(popSize);
                for(int i=0;i<popSize;i++) {
                    people.add(new HashMap());
                }
                groups = new ArrayList<>(popSize);
                for(int i=0;i<popSize;i++) {
                    groups.add(new HashMap());
                }
                projects = new ArrayList<>(popSize);
                for(int i=0;i<popSize;i++) {
                    projects.add(new HashMap());
                }
                rooms = new ArrayList<>(popSize);
                for(int i=0;i<popSize;i++) {
                    rooms.add(new HashMap());
                }
                largeRooms = new ArrayList<>(popSize);
                for(int i=0;i<popSize;i++) {
                    largeRooms.add(new HashMap());
                }
                smallRooms = new ArrayList<>(popSize);
                for(int i=0;i<popSize;i++) {
                    smallRooms.add(new HashMap());
                }
                closeTo = new HashSet();

	}

	/**
	 * A getter for the global instance of this class.  If an instance of this class
	 * does not already exist, it will be created.
	 * @return The singleton (global) instance.
	 */
	public static Environment get(int popSize) {
		if (instance==null) instance = new Environment(null);
		return instance;
	}
        
        public PopMember createPopulationMember(int index, LinkedList<Person> managerQ, LinkedList<Person> groupHeadQ, LinkedList<Person> projectHeadQ, LinkedList<Person> secretaryQ, LinkedList<Person> personQ, Room[] roomAddresses, Room[] largeRoomAddresses, Room[] smallRoomAddresses) throws FullRoomException {
            PopMember p = new PopMember(worksWith, people.get(index), groups.get(index), projects.get(index), closeTo, managerQ, groupHeadQ, projectHeadQ, secretaryQ, personQ, roomAddresses, largeRoomAddresses, smallRoomAddresses);
            return p;
        }

        @Override
        public int fromFile(String fileName) {
		int ret = super.fromFile(fileName);
                if(ret >= 0) {
                    // Open output file and print predicates
                    try {
                        PrintWriter writer = new PrintWriter(fileName + ".out", "UTF-8");

                        printRoomPredicates(writer);
                        printGroupPredicates(writer);
                        printProjectPredicates(writer);
                        printPeoplePredicates(writer);

                        writer.close();
                    } catch (FileNotFoundException | UnsupportedEncodingException e) {
                        error("Can't open file " + fileName + ".out");
                        return -1;
                    }
                }

                return ret;
	}

        private void printPeoplePredicates(PrintWriter writer) {
            Iterator<Person> peopleIter = people.values().iterator();
            while (peopleIter.hasNext()){
                Person tempPerson = peopleIter.next();
                String tempName = tempPerson.getName();

                //Printing the name of the person
                writer.println("person(" + tempName + ")");

                //Printing the roles of the person
                if(tempPerson.isHacker()){
                    writer.println("hacker(" + tempName + ")");
                }
                if(tempPerson.isManager()){
                    writer.println("manager(" + tempName + ")");
                }
                if(tempPerson.isResearcher()){
                    writer.println("researcher(" + tempName + ")");
                }
                if(tempPerson.isSecretary()){
                    writer.println("secretary(" + tempName + ")");
                }
                if(tempPerson.isSmoker()){
                    writer.println("smoker(" + tempName + ")");
                }
            }

            // Printing the works with array
            Iterator<SymmetricPair<Person, Person>> worksWithIter = worksWith.iterator();
            while (worksWithIter.hasNext()) {
                SymmetricPair<Person, Person> relation = worksWithIter.next();
                writer.println("works-with(" + relation.left.getName() + ", " + relation.right.getName() + ")");
                writer.println("works-with(" + relation.right.getName() + ", " + relation.left.getName() + ")");
            }
        }

        private void printRoomPredicates(PrintWriter writer) {
            // NON-FUNCTIONAL
            Iterator<Room> roomIter = rooms.values().iterator();
            while (roomIter.hasNext()) {
                Room room = roomIter.next();

                // Print room name
                writer.println("room(" + room.getName() + ")");

                // Print room size
                switch (room.getSize()) {
                    case SMALL:
                        writer.println("small-room(" + room.getName() + ")");
                        break;
                    case MEDIUM:
                        writer.println("medium-room(" + room.getName() + ")");
                        break;
                    case LARGE:
                        writer.println("large-room(" + room.getName() + ")");
                        break;
                }

                // Print people assigned to this room
                Person[] assignedPeople = room.getAssignedPeople();
                if (assignedPeople[0] != null) {
                    writer.println("assign-to(" + assignedPeople[0].getName() + ", " + room.getName() + ")");
                }
                if (assignedPeople[1] != null) {
                    writer.println("assign-to(" + assignedPeople[1].getName() + ", " + room.getName() + ")");
                }
            }

            // Print close relation predicates
            Iterator<SymmetricPair<Room,Room>> closeToIter = closeTo.iterator();
            while(closeToIter.hasNext()) {
                SymmetricPair<Room,Room> relation = closeToIter.next();
                writer.println("close(" + relation.left.getName() + ", " + relation.right.getName() + ")");
                writer.println("close(" + relation.right.getName() + ", " + relation.left.getName() + ")");
            }
        }
        private void printGroupPredicates(PrintWriter writer){
            Iterator<Group> groupIter = groups.values().iterator();
                while(groupIter.hasNext()){
                    Group group = groupIter.next();
                    writer.println("group(" + group.getName() + ")");
                    Iterator<Person> headIterator = group.getHeadMap().values().iterator();
                    while (headIterator.hasNext()) {
                        Person tempPerson = headIterator.next();    //gets every person that is a head in the group
                        writer.println("heads-group(" + tempPerson.getName() + ", " + group.getName() + ")");
                    }
                    
                    Iterator<Person> personIter = group.getPersonMap().values().iterator();
                    while(personIter.hasNext()) {
                        Person tempPerson = personIter.next();
                        writer.println("group(" + tempPerson.getName() + "," + group.getName() + ")");
                    }
                    
                    Iterator<Person> secretaryIter = group.getSecretaryMap().values().iterator();
                    while(secretaryIter.hasNext()) {
                        Person tempPerson = secretaryIter.next();
                        writer.println("group(" + tempPerson.getName() + "," + group.getName() + ")");
                    }
                    
                    Iterator<Person> managerIter = group.getManagerMap().values().iterator();
                    while(managerIter.hasNext()) {
                        Person tempPerson = managerIter.next();
                        writer.println("group(" + tempPerson.getName() + "," + group.getName() + ")");
                    }
                }
        }

        private void printProjectPredicates(PrintWriter writer){
            Iterator<Project> projectIter = projects.values().iterator();
                while(projectIter.hasNext()){
                    Project project = projectIter.next();
                    writer.println("project(" + project.getName() + ")");
                    if (project.getLarge()){
                        writer.println("large-project(" + project.getName() + ")");
                    }
                    
                    Iterator<Person> headIterator = project.getHeads();
                    while (headIterator.hasNext()) {
                        Person tempPerson = headIterator.next();    //gets every person that is a head in the group
                        writer.println("heads-project(" + tempPerson.getName() + ", " + project.getName() + ")");
                    }
                    
                    ArrayList<Person> projectMembers = project.getPersonList();
                    for (Person tempPerson : projectMembers) {
                        writer.println("project(" + tempPerson.getName() + "," + project.getName() + ")");
                    }
                }
        }
        
        @Override
	public void a_person(String p) {
            for (HashMap<String, Person> peopleMap : people ) {
                 // Check to see if there is a person of name p.
                 // If there is then do nothing
                if(peopleMap.containsKey(p)){
                    // Do nothing (Because there is already a person with that name)

                 }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                peopleMap.put(p, newPerson);
            }
            }
        }

        @Override
	public boolean e_person(String p) {
            //Checks if a person exists with the name p
            return true; // people.containsKey(p);
        }

        @Override
	public void a_secretary(String p) {
            for (HashMap<String, Person> peopleMap : people) {


                // Check to see if there is a person of name p.
                // If there is then grab the person and make them a secretary.
                if (peopleMap.containsKey(p)) {
                    Person personWithNameP;
                    personWithNameP = peopleMap.get(p);
                    personWithNameP.setSecretary(true);
                    ArrayList<Group> personGroups = personWithNameP.getGroups();

                    for (Group i : personGroups) {
                        i.addToGroup(personWithNameP);
                    }
                }
                // If no person exists:
                else {
                    Person newPerson = new Person(p);
                    newPerson.setSecretary(true);
                    peopleMap.put(p, newPerson);
                }
            }
        }
        @Override
	public boolean e_secretary(String p) {
            // If the person exists and is a secretary then return true
           /* if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isSecretary()){
                    return true;
                }
            }
           */ // Else return false
            return false;
        }

        @Override
	public void a_researcher(String p) {

            for (HashMap<String, Person> peopleMap : people) {
            // Check to see if there is a person of name p.
            // If there is then grab the person and make them a researcher.
            if(peopleMap.containsKey(p)){
                Person personWithNameP;
                personWithNameP = peopleMap.get(p);
                personWithNameP.setResearcher(true);
            }
            // If no person exists:
            else{
                Person newPerson = new Person(p);
                newPerson.setResearcher(true);
                peopleMap.put(p, newPerson);
                }
            }
        }
        @Override
	public boolean e_researcher(String p) {
       /*     // If the person exists and is a researcher then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isResearcher()){
                    return true;
                }
            }
         */   // Else return false
            return false;
        }


        @Override
	public void a_manager(String p) {
            for (HashMap<String, Person> peopleMap : people) {
                // Check to see if there is a person of name p.
                // If there is then grab the person and make them a manager.
                if (peopleMap.containsKey(p)) {
                    Person personWithNameP;
                    personWithNameP = peopleMap.get(p);
                    personWithNameP.setManager(true);
                    ArrayList<Group> personGroups = personWithNameP.getGroups();

                    for (Group i : personGroups) {
                        i.addToGroup(personWithNameP);
                    }
                }
                // If no person exists:
                else {
                    Person newPerson = new Person(p);
                    newPerson.setManager(true);
                    peopleMap.put(p, newPerson);
                }
            }
        }
        @Override
	public boolean e_manager(String p) {
          /*  // If the person exists and is a manager then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isManager()){
                    return true;
                }
            }
            */// Else return false
            return false;
        }

        @Override
	public void a_smoker(String p) {
            for (HashMap<String, Person> peopleMap : people) {
                // Check to see if there is a person of name p.
                // If there is then grab the person and make them a smoker.
                if (peopleMap.containsKey(p)) {
                    Person personWithNameP;
                    personWithNameP = peopleMap.get(p);
                    personWithNameP.setSmoker(true);
                }
                // If no person exists:
                else {
                    Person newPerson = new Person(p);
                    newPerson.setSmoker(true);
                    peopleMap.put(p, newPerson);
                }
            }
        }
        @Override
	public boolean e_smoker(String p) {
            // If the person exists and is a smoker then return true
          /*  if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isSmoker()){
                    return true;
                }
            }
            // Else return false
            */return false;
        }

        @Override
	public void a_hacker(String p) {
            for (HashMap<String, Person> peopleMap : people) {
                // Check to see if there is a person of name p.
                // If there is then grab the person and make them a hacker.
                if (peopleMap.containsKey(p)) {
                    Person personWithNameP;
                    personWithNameP = peopleMap.get(p);
                    personWithNameP.setHacker(true);
                }
                // If no person exists:
                else {
                    Person newPerson = new Person(p);
                    newPerson.setHacker(true);
                    peopleMap.put(p, newPerson);
                }
            }
        }
        @Override
	public boolean e_hacker(String p) {
       /*     // If the person exists and is a hacker then return true
            if(people.containsKey(p)){
                Person tempPerson = people.get(p);
                if(tempPerson.isHacker()){
                    return true;
                }
            }
         */   // Else return false
            return false;
        }

        @Override
	public void a_group(String p, String grp) {
            //if group exists, add person to group
            for(int i=0;i<groups.size();i++) {
                HashMap<String, Group> groupMap = groups.get(i);
                HashMap<String, Person> personMap = people.get(i);
                
                Group tempGroup;
                Person tempPerson;
                //group check
                if (groupMap.containsKey(grp)) {
                    tempGroup = groupMap.get(grp);
                } else {
                    tempGroup = new Group(grp);
                    groupMap.put(grp, tempGroup);
                }
                //person check
                if (personMap.containsKey(p)) {
                    tempPerson = personMap.get(p);
                    tempGroup.addToGroup(tempPerson);
                    tempPerson.addGroup(tempGroup);         //update person's group array
                } else {
                    tempPerson = new Person(p);
                    tempGroup.addToGroup(tempPerson);
                    personMap.put(p, tempPerson);
                    tempPerson.addGroup(tempGroup);        //update person's group array
                }
            }
        }

        @Override
	public boolean e_group(String p, String grp) {
            //if p is a person in grp, return true
            /*if (groups.containsKey(grp)){
                Group tempGroup = groups.get(grp);
                if (people.containsKey(p)){
                    Person tempPerson = people.get(p);
                    return tempGroup.memberOfGroup(tempPerson);
                }
                else
                    return false;
            }
            else
                return false;*/
            return true;
        }

        @Override
	public void a_project(String p, String prj) {
            for(int i=0;i<projects.size();i++) {
                HashMap<String, Project> projectMap = projects.get(i);
                HashMap<String, Person> personMap = people.get(i);
                
                Project project;
                Person person;
                if (projectMap.containsKey(prj)) {
                    project = projectMap.get(prj);
                } else {
                    project = new Project(prj);
                    projectMap.put(prj, project);
                }

                if (personMap.containsKey(p)) {
                    person = personMap.get(p);
                    project.setProjectPerson(person);
                    person.addProject(project);
                } else {
                    person = new Person(p);
                    project.setProjectPerson(person);
                    person.addProject(project);

                    personMap.put(p, person);
                }
            }
        }
        @Override
	public boolean e_project(String p, String prj) {
            // NON-FUNCTIONAL
            return false;
        }

        @Override
	public void a_heads_group(String p, String grp) {
            for(int i=0;i<groups.size();i++) {
                HashMap<String, Group> groupMap = groups.get(i);
                HashMap<String, Person> personMap = people.get(i);
                
                //group check
                Group tempGroup;
                Person tempPerson;
                if (groupMap.containsKey(grp)) {
                    tempGroup = groupMap.get(grp);
                } else {
                    tempGroup = new Group(grp);
                    groupMap.put(grp, tempGroup);
                }
                //person check and assignment
                if (personMap.containsKey(p)) {
                    tempPerson = personMap.get(p);
                    tempGroup.addAsHead(tempPerson);
                    tempPerson.addGroup(tempGroup);
                    tempPerson.setGroupHead(true);
                } else {
                    tempPerson = new Person(p);
                    tempGroup.addAsHead(tempPerson);
                    tempPerson.addGroup(tempGroup);
                    tempPerson.setGroupHead(true);

                    personMap.put(p, tempPerson);
                }
            }
        }

        @Override
	public boolean e_heads_group(String p, String grp) {
            return true;
        }

        @Override
	public void a_heads_project(String p, String prj) {
            a_project(p,prj);
            
            for(int i=0;i<projects.size();i++) {
                HashMap<String, Project> projectMap = projects.get(i);
                HashMap<String, Person> personMap = people.get(i);
                
                Person person = personMap.get(p);
                projectMap.get(prj).setProjectHead(person);
                person.setProjectHead(true);
            }
        }
        @Override
	public boolean e_heads_project(String p, String prj) {
            //return e_project(p,prj) && projects.get(prj).checkHead(p);
            return true;
        }

        @Override
	public void a_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s){
            Person thisPerson;
            
            for(HashMap<String,Person> personMap : people) {
                if (personMap.containsKey(p)) {
                    thisPerson = personMap.get(p);
                } else {
                    thisPerson = new Person(p);
                    personMap.put(p, thisPerson);
                }
            }
            
            HashMap<String, Person> aPersonMap = people.get(0);

            // Iterate through all workers in TreeSet, if any coworkers
            // don't exist create them then add the workswith relation
            Iterator<Pair<ParamType,Object>> iter = p2s.iterator();
            while(iter.hasNext()) {
                Person person_i;
                String name_i = (String)iter.next().getValue();

                // If the person we are iterating through the tree exists,
                // Grab the person and add the works with relation
                if(aPersonMap.containsKey(name_i)){
                    person_i = aPersonMap.get(name_i);
                }
                else{
                    for(HashMap<String,Person> personMap : people) {
                        person_i = new Person(name_i);
                        personMap.put(name_i, person_i);
                    }
                }
                
                worksWith.add(new SymmetricPair(person_i, thisPerson));
            }
            
        }
        @Override
	public boolean e_works_with(String p, TreeSet<Pair<ParamType,Object>> p2s) {
            return true;
        }


        @Override
	public void a_works_with(String p, String p2) {
            Person personObj1, personObj2;

            // First check if the specified people exist, if not
            // create them

            for(HashMap<String,Person> personMap : people) {
                if (!personMap.containsKey(p)) {
                    personObj1 = new Person(p);
                    personMap.put(p, personObj1);
                } else {
                    personObj1 = personMap.get(p);
                }

                if (!personMap.containsKey(p2)) {
                    personObj2 = new Person(p2);
                    personMap.put(p2, personObj2);
                } else {
                    personObj2 = personMap.get(p2);
                }
            }

            // Create a new relation pair (person1, person2) and add
            // it to the set of relations
            //
            // Note that duplicate pairs are not added by defition
            // of the add method in HashSet including pairs of the
            // form (person2, person1)
            worksWith.add(new SymmetricPair(personObj1, personObj2));
        }
        @Override
	public boolean e_works_with(String p, String p2) {
            return true;
        }

        @Override
	public void a_assign_to(String p, String room) throws FullRoomException {
            for (int i = 0; i < largeRooms.size(); i ++) {
                HashMap<String, Room> roomMap = rooms.get(i);
                HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                HashMap<String, Room> largeRoomMap = largeRooms.get(i);
                HashMap<String, Person> peopleMap = people.get(i);
            Person person;
            Room   roomObj;

            if(peopleMap.containsKey(p)) {
                person = peopleMap.get(p);
                person.setFixed(true);
            } else {
                person = new Person(p);
                person.setFixed(true);
                peopleMap.put(p, person);
            }

            if(roomMap.containsKey(room)) {
                roomObj = roomMap.get(room);
            } else if(smallRoomMap.containsKey(room)) {
                roomObj = smallRoomMap.get(room);
            } else if(largeRoomMap.containsKey(room)) {
                roomObj = largeRoomMap.get(room);
            } else {
                roomObj = new Room(room, RoomSize.MEDIUM);
                roomMap.put(room, roomObj);
            }

            roomObj.setHasFixedAssignments();

            roomObj.putPerson(person);
            person.assignToRoom(roomObj);
            //if person is manager then remove room from selection?


            }
        }

        @Override
	public boolean e_assign_to(String p, String room) {
            // Check if the person exists
            /*
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
            return assignedRoom.getName().equals(room); */
            return true;
        }

	// ROOMS
        @Override
	public void a_room(String r) {
            // Add room if it doesn't already exist
            a_medium_room(r);
        }
        @Override
	public boolean e_room(String r) {
            return true;//rooms.containsKey(r);
        }

        @Override
	public void a_close(String room, String room2) {
            Room roomObj1, roomObj2;
            
            for (int i = 0; i < largeRooms.size(); i++) {
                HashMap<String, Room> roomMap = rooms.get(i);
                HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                HashMap<String, Room> largeRoomMap = largeRooms.get(i);

                // First check if the specified rooms exist, if not
                // create them
                if(roomMap.containsKey(room)) {
                    roomObj1 = roomMap.get(room);
                } else if(smallRoomMap.containsKey(room)) {
                    roomObj1 = smallRoomMap.get(room);
                } else if(largeRoomMap.containsKey(room)) {
                    roomObj1 = largeRoomMap.get(room);
                } else {
                    roomObj1 = new Room(room, RoomSize.MEDIUM);
                    roomMap.put(room, roomObj1);
                }

                if(roomMap.containsKey(room2)) {
                    roomObj2 = roomMap.get(room2);
                } else if(smallRoomMap.containsKey(room2)) {
                    roomObj2 = smallRoomMap.get(room2);
                } else if(largeRoomMap.containsKey(room2)) {
                    roomObj2 = largeRoomMap.get(room2);
                } else {
                    roomObj2 = new Room(room2, RoomSize.MEDIUM);
                    roomMap.put(room2, roomObj2);
                }
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
            return true;
        }

        @Override
	public void a_close(String room, TreeSet<Pair<ParamType,Object>> set) {
            Room roomObj1;

            for (int i = 0; i < largeRooms.size(); i++) {
                HashMap<String, Room> roomMap = rooms.get(i);
                HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                HashMap<String, Room> largeRoomMap = largeRooms.get(i);
                
                if(roomMap.containsKey(room)) {
                    roomObj1 = roomMap.get(room);
                } else if(smallRoomMap.containsKey(room)) {
                    roomObj1 = smallRoomMap.get(room);
                } else if(largeRoomMap.containsKey(room)) {
                    roomObj1 = largeRoomMap.get(room);
                } else {
                    roomObj1 = new Room(room, RoomSize.MEDIUM);
                    roomMap.put(room, roomObj1);
                }
            }

            // Iterate through all rooms in TreeSet, if any rooms
            // don't exist create them then add the close relation
            // (room1, room_i) to the closeTo set
            Iterator<Pair<ParamType,Object>> iter = set.iterator();
            while(iter.hasNext()) {
                Room room_i;
                String roomName_i = (String)iter.next().getValue();
                
                for (int i = 0; i < largeRooms.size(); i++) {
                    HashMap<String, Room> roomMap = rooms.get(i);
                    HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                    HashMap<String, Room> largeRoomMap = largeRooms.get(i);

                    if (roomMap.containsKey(roomName_i)) {
                        room_i = roomMap.get(roomName_i);
                    } else if (smallRoomMap.containsKey(roomName_i)) {
                        room_i = smallRoomMap.get(roomName_i);
                    } else if (largeRoomMap.containsKey(roomName_i)) {
                        room_i = largeRoomMap.get(roomName_i);
                    } else {
                        room_i = new Room(roomName_i, RoomSize.MEDIUM);
                        roomMap.put(roomName_i, room_i);
                    }
                }

                closeTo.add(new SymmetricPair(roomObj1, room_i));
            }
        }
        @Override
	public boolean e_close(String room, TreeSet<Pair<ParamType,Object>> set) {
            return true;
        }

        @Override
	public void a_large_room(String r) {
            for (int i = 0; i < largeRooms.size(); i++) {
                HashMap<String, Room> roomMap = rooms.get(i);
                HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                HashMap<String, Room> largeRoomMap = largeRooms.get(i);

                if (!roomMap.containsKey(r) && !smallRoomMap.containsKey(r)) {
                    // Add room if it doesn't already exist
                    if (!largeRoomMap.containsKey(r)) {
                        largeRoomMap.put(r, new Room(r, RoomSize.LARGE));
                    }
                } else if (!smallRoomMap.containsKey(r)) {
                    // If the room already exists update the size
                    Room room = roomMap.remove(r);
                    room.setSize(RoomSize.LARGE);
                    largeRoomMap.put(r, room);

                } else if (!roomMap.containsKey(r)) {
                    // If the room already exists update the size
                    Room room = smallRoomMap.remove(r);
                    room.setSize(RoomSize.LARGE);
                    largeRoomMap.put(r, room);

                }
            }
        }
        @Override
	public boolean e_large_room(String r) {
            return true;//(largeRooms.containsKey(r) );
        }

        @Override
	public void a_medium_room(String r) {
            for (int i = 0; i < largeRooms.size(); i++) {
                HashMap<String, Room> roomMap = rooms.get(i);
                HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                HashMap<String, Room> largeRoomMap = largeRooms.get(i);

                if (!smallRoomMap.containsKey(r) && !largeRoomMap.containsKey(r)) {
                    // Add room if it doesn't already exist
                    if (!roomMap.containsKey(r)) {
                        roomMap.put(r, new Room(r, RoomSize.MEDIUM));
                    }
                } else if (!largeRoomMap.containsKey(r)) {
                    // If the room already exists update the size
                    Room room = smallRoomMap.remove(r);
                    room.setSize(RoomSize.MEDIUM);
                    roomMap.put(r, room);
                } else if (!smallRoomMap.containsKey(r)) {
                    // If the room already exists update the size
                    Room room = largeRoomMap.remove(r);
                    room.setSize(RoomSize.MEDIUM);
                    roomMap.put(r, room);
                }
            }
        }
        @Override
	public boolean e_medium_room(String r) {
            return true; //rooms.containsKey(r);
        }

        @Override
	public void a_small_room(String r) {
            for (int i = 0; i < largeRooms.size(); i++) {
                HashMap<String, Room> roomMap = rooms.get(i);
                HashMap<String, Room> smallRoomMap = smallRooms.get(i);
                HashMap<String, Room> largeRoomMap = largeRooms.get(i);

                if (!roomMap.containsKey(r) && !largeRoomMap.containsKey(r)) {
                    // Add room if it doesn't already exist
                    if (!smallRoomMap.containsKey(r)) {
                        smallRoomMap.put(r, new Room(r, RoomSize.SMALL));
                    }
                } else if (!largeRoomMap.containsKey(r)) {
                    // If the room already exists update the size
                    Room room = roomMap.remove(r);
                    room.setSize(RoomSize.SMALL);
                    smallRoomMap.put(r, room);
                } else if (!roomMap.containsKey(r)) {
                    // If the room already exists update the size
                    Room room = largeRoomMap.remove(r);
                    room.setSize(RoomSize.SMALL);
                    smallRoomMap.put(r, room);
                }
            }
        }
        @Override
	public boolean e_small_room(String r) {
            return true;//smallRooms.containsKey(r);
        }

	// GROUPS
        @Override
	public void a_group(String g) {
            for(HashMap<String, Group> groupMap : groups) {
                //add group if group doesn't exist
                if (!groupMap.containsKey(g)) {
                    Group tempGroup = new Group(g);
                    groupMap.put(g, tempGroup);
                }
                //else do nothing.
            }
        }
        @Override
	public boolean e_group(String g) {
            return true;
        }

	// PROJECTS
        @Override
	public void a_project(String p) {
            for(HashMap<String, Project> projectMap : projects) {
                if (!projectMap.containsKey(p)) {
                    Project newProject = new Project(p);
                    projectMap.put(p, newProject);
                }
            }

        }
        @Override
	public boolean e_project(String p) {
            return true;
        }

        @Override
	public void a_large_project(String prj) {
            for(HashMap<String, Project> projectMap : projects) {
                if (!projectMap.containsKey(prj)) {
                    Project newProject = new Project(prj);
                    newProject.setLarge();
                    projectMap.put(prj, newProject);
                } else {
                    Project project = projectMap.get(prj);
                    project.setLarge();
                }
            }

        }
        @Override
	public boolean e_large_project(String prj) {
            return true;
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
