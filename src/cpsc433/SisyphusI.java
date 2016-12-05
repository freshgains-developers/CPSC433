package cpsc433;

import java.util.*;

/**
 * This is the main class for the SysiphusI assignment.  It's main function is to
 * interpret the command line.
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
public class SisyphusI {

	/**
	 * Merely create a new SisypyusI object, and let the constructor run the program.
	 * @param args The command line argument list
	 */
	public static void main(String[] args) {
		new SisyphusI(args);
	}

	private int SWAP_TOTAL = 4;

	protected final String[] args;
	protected String out;
	protected Environment env;
        
        private final int POP_SIZE = 100;
        
        private PopMember bestPopMember = null;
        private int bestScore = Integer.MIN_VALUE;


	public SisyphusI(String[] args) {
		this.args = args;
		run();
	}

	protected void run() {
                env = getEnvironment();

		String fromFile = null;

		if (args.length>0) {
			fromFile = args[0];
			env.fromFile(fromFile);
		}
		else {
			printSynopsis();
		}

		out = fromFile+".out";

		createShutdownHook();

		if (args.length>1) { // using command-line arguments
			runCommandLineMode();
			killShutdownHook();
		}
		else { // using interactive mode
			runInteractiveMode();
			killShutdownHook();
		}
	}

	/**
	 * Return the environment object.  One should return an environment object that
	 * makes sense for YOUR solution to the problem: the environment could contain
	 * all the object instances required for the domain (like people, rooms, etc),
	 * as well as potential solutions and partial solutions.
	 * @return The global environment object.
	 */
	protected Environment getEnvironment() {
		return Environment.get(POP_SIZE);
	}

	protected void printSynopsis() {
		System.out.println("Synopsis: SisyphusI [<env-file> [<time-in-ms>]]");
	}

	/**
	 * If you want to install a shutdown hook, you can do that here.  A shutdown
	 * hook is completely optional, but can be useful if you search doesn't exit
	 * in a timely manner.
	 */
	protected void createShutdownHook() {}
	protected void killShutdownHook() {}

	/**
	 * Run in "Command line mode", that is, batch mode.
	 */
	protected void runCommandLineMode() {
		try {
			long timeLimit = Long.parseLong(args[1]);

                        if(timeLimit > 0) {
                            Timer timer = new Timer();
                            timer.schedule((new TimerTask() {
                                @Override
                                public void run() {
                                    printResults();
                                    System.exit(0);
                                }
                            }), (long) (timeLimit * 0.9)); 
                        }
                        
			//timeLimit -= (System.currentTimeMillis()-startTime);
			System.out.println("Performing search for "+timeLimit+"ms\n");
			try {
				doSearch(env, timeLimit);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		catch (NumberFormatException ex) {
			System.out.println("Error: The 2nd argument must be a long integer.");
			printSynopsis();
			System.exit(-1);
		}
		printResults();
	}

	/**
	 * Perform the actual search
	 * @param env An Environment object.
	 * @param timeLimit A time limit in milliseconds.
	 * @throws cpsc433.Environment.UnsolvableInstanceException
	 */
	protected void doSearch(Environment env, long timeLimit) throws Environment.UnsolvableInstanceException, Room.FullRoomException {

		// The array that holds all the pop members
		PopMember[] population = new PopMember[POP_SIZE];
		int[] populationScores = new int[POP_SIZE];

            for (int popIndex = 0; popIndex < POP_SIZE; popIndex++) {
                Iterator<Person> peopleIter = env.people.get(popIndex).values().iterator();

                LinkedList<Person> managerQ = new LinkedList();
                LinkedList<Person> groupHeadQ = new LinkedList();
                LinkedList<Person> projectHeadQ = new LinkedList();
                LinkedList<Person> secretaryQ = new LinkedList();
                LinkedList<Person> personQ = new LinkedList();

                Room[] roomAddresses = (Room[]) env.rooms.get(popIndex).values().toArray(new Room[0]);
                Room[] largeRoomAddresses = (Room[]) env.largeRooms.get(popIndex).values().toArray(new Room[0]);
                Room[] smallRoomAddresses = (Room[]) env.smallRooms.get(popIndex).values().toArray(new Room[0]);



				// identify all managers, group heads, project heads, secretaries
				// and assign to proper queues
                while (peopleIter.hasNext()) {
                    Person tempPerson = peopleIter.next();

                    if (tempPerson.isGroupHead()) {
                        groupHeadQ.add(tempPerson);
                    } else if (tempPerson.isManager()) {
                        managerQ.add(tempPerson);
                    } else if (tempPerson.isProjectHead()) {
                        projectHeadQ.add(tempPerson);
                    } else if (tempPerson.isSecretary()) {
                        secretaryQ.add(tempPerson);
                    } else {
                        personQ.add(tempPerson);
                    }
                }

                //cutoff for unsolvable instances. Dependant on number of people, rooms, managers, and heads.
                int proods = managerQ.size() + groupHeadQ.size() + projectHeadQ.size();
                if (env.people.get(popIndex).size() - proods <= 2 * (env.rooms.get(popIndex).size() + env.smallRooms.get(popIndex).size() + env.largeRooms.get(popIndex).size() - proods)) {
                    try {
                        PopMember p = env.createPopulationMember(popIndex, managerQ, groupHeadQ, projectHeadQ, secretaryQ, personQ, roomAddresses, largeRoomAddresses, smallRoomAddresses);
						population[popIndex] = p;

                    } catch (Room.FullRoomException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // TODO: Remove this exception and replace with comment write
                    throw new Environment.UnsolvableInstanceException("Instance is unsolvable. No solution possible.");
                }

            }

		Random rand = new Random();

		// mutations loop
		while(1 == 1){
			int totalScore = 0;
			for (int popIndex = 0; popIndex < POP_SIZE; popIndex++) {
				// Saving previous scores
				int tempScore = population[popIndex].score();
				populationScores[popIndex] = tempScore;
				totalScore += tempScore;
                                
                                if(tempScore >= bestScore) {
                                    bestScore = tempScore;
                                    bestPopMember = population[popIndex];
                                    
                                    population[popIndex].printAssignments();
                                    System.out.println("Score: " + tempScore);
                                    System.out.println();
                                }

				// Finds new scores
				population[popIndex].mutate(SWAP_TOTAL);
			}
			// Average
			int averageScore = totalScore / POP_SIZE;

			// Compares the scores and decides if we should be mutating
			for (int popIndex = 0; popIndex < POP_SIZE; popIndex++) {

				int newScore = population[popIndex].score();
				// Checks to see if the original was better than the new mutation
				if (averageScore > newScore && rand.nextInt(100) > 10) {
					population[popIndex].rollback();
				}
			}
		}

	}


	protected void printResults() {
		/*Iterator<Room> iter1 = env.smallRooms.values().iterator();
		Iterator<Room> iter2 = env.rooms.values().iterator();
		Iterator<Room> iter3 = env.largeRooms.values().iterator();

		for (Room i : env.smallRooms.values()) {
			if (i.getAssignedPeople()[0] != null) {
				System.out.println("assign-to(" + i.getAssignedPeople()[0].getName() + ", " + i.getName() +")");
			}

			if (i.getAssignedPeople()[1]  != null ) {
				System.out.println("assign-to(" + i.getAssignedPeople()[1].getName() + ", " + i.getName() +")");
			}
		}

		for (Room i : env.rooms.values()) {
			if (i.getAssignedPeople()[0] != null) {
				System.out.println("assign-to(" + i.getAssignedPeople()[0].getName() + ", " + i.getName() +")");
			}

			if (i.getAssignedPeople()[1]  != null ) {
				System.out.println("assign-to(" + i.getAssignedPeople()[1].getName() + ", " + i.getName() +")");
			}
		}

		for (Room i : env.largeRooms.values()) {
			if (i.getAssignedPeople()[0] != null) {
				System.out.println("assign-to(" + i.getAssignedPeople()[0].getName() + ", " + i.getName() +")");
			}

			if (i.getAssignedPeople()[1]  != null ) {
				System.out.println("assign-to(" + i.getAssignedPeople()[1].getName() + ", " + i.getName() +")");
			}
		}*/



	}

	protected void runInteractiveMode() {
		final int maxBuf = 200;
		byte[] buf = new byte[maxBuf];
		int length;

		Scanner scan = new Scanner(System.in);
		try {
			System.out.print("\nSisyphus I: query using predicates, assert using \"!\" prefixing predicates;\n !exit() to quit; !help() for help.\n\n> ");
			while (scan.hasNextLine()) {
				String s = scan.nextLine();
				s = s.trim();
				if (s.equals("exit")) break;
				if (s.equals("?")||s.equals("help")) {
					s = "!help()";
					System.out.println("> !help()");
				}
				if (s.length()>0) {
					if (s.charAt(0)=='!')
						env.assert_(s.substring(1));
					else
						System.out.print(" --> "+env.eval(s));
				}
				System.out.print("\n> ");
			}
		} catch (Exception e) {
			System.err.println("exiting: "+e.toString());
		}
	}
}
