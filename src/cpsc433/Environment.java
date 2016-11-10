/**
 * 
 */
package cpsc433;

import cpsc433.Predicate.ParamType;
import java.util.ArrayList;
import java.util.HashMap;
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
        
        private HashMap<String, HashMap<String, Person>> worksWith = null;
        private HashMap<String, Person> people = null;
        private ArrayList<Group> groups = null;
        private ArrayList<Project> projects = null;
	
	protected Environment(String name) {
		super(name==null?"theEnvironment":name);
                
                worksWith = new HashMap();
                people = new HashMap();
                groups = new ArrayList();
                projects = new ArrayList();
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
            
        }
        @Override
	public boolean e_person(String p) {
            return false;
        }
	
        @Override
	public void a_secretary(String p) {
            
        }
        @Override
	public boolean e_secretary(String p) {
            return false;
        }
	
        @Override
	public void a_researcher(String p) {
            
        }
        @Override
	public boolean e_researcher(String p) {
            return false;
        }
	
        @Override
	public void a_manager(String p) {
            
        }
        @Override
	public boolean e_manager(String p) {
            return false;
        }
	
        @Override
	public void a_smoker(String p) {
            
        }
        @Override
	public boolean e_smoker(String p) {
            return false;
        }
	
        @Override
	public void a_hacker(String p) {
            
        }
        @Override
	public boolean e_hacker(String p) {
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
            
        }
        @Override
	public boolean e_project(String p, String prj) {
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
            
        }
        @Override
	public boolean e_heads_project(String p, String prj) {
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
            
        }
        @Override
	public boolean e_assign_to(String p, String room) {
            return false;
        }

	// ROOMS
        @Override
	public void a_room(String r) {
            
        }	
        @Override
	public boolean e_room(String r) {
            return false;
        }
	
        @Override
	public void a_close(String room, String room2) {
            
        }	
        @Override
	public boolean e_close(String room, String room2) {
            return false;
        }

        @Override
	public void a_close(String room, TreeSet<Pair<ParamType,Object>> set) {
            
        }
        @Override
	public boolean e_close(String room, TreeSet<Pair<ParamType,Object>> set) {
            return false;
        }
	
        @Override
	public void a_large_room(String r) {
            
        }
        @Override
	public boolean e_large_room(String r) {
            return false;
        }
	
        @Override
	public void a_medium_room(String r) {
            
        }
        @Override
	public boolean e_medium_room(String r) {
            return false;
        }
	
        @Override
	public void a_small_room(String r) {
            
        }
        @Override
	public boolean e_small_room(String r) {
            return false;
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
            
        }
        @Override
	public boolean e_project(String p) {
            return false;
        }
	
        @Override
	public void a_large_project(String prj) {
            
        }
        @Override
	public boolean e_large_project(String prj) {
            return false;
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
