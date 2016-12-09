package cpsc433;

import static cpsc433.PredicateReader.error;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * This is the main class for the SysiphusI assignment.  It's main function is to
 * interpret the command line.
 * <p>
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
 */
public class SisyphusI {

    /**
     * Merely create a new SisypyusI object, and let the constructor run the program.
     *
     * @param args The command line argument list
     */
    public static void main(String[] args) {
        new SisyphusI(args);
    }

    // Default for mutation per generation
    private int SWAP_TOTAL = 1;

    protected final String[] args;
    protected String out;
    protected Environment env;

    // Default for total population size of facts
    private int POP_SIZE = 50;

    private boolean printAfter = false;
    private boolean printFirst = false;

    // Where we store the best assignment we have found (and score)
    private LinkedHashSet<Room> bestAssignments = null;
    private int bestScore = Integer.MIN_VALUE;

    private long timerStart;


    public SisyphusI(String[] args) {
        this.args = args;
        run();
    }

    protected void run() {
        // Takes the time limit and sets the POP_SIZE to be a factor of the total amount of time
        // This is because, the more time we have, the more we can generate at the same time.
        long timeLimit = Long.parseLong(args[1]);
        if (timeLimit > 0) {
            // Caps the size for efficancy sake
            POP_SIZE = ((int) (timeLimit * 0.001)) + 20;
            if (POP_SIZE > 1000) {
                POP_SIZE = 1000;
            }
        }
        env = getEnvironment();

        String fromFile = null;

        if (args.length > 0) {

            if (timeLimit > 0) {
                // Changes SWAP_TOTAL with respect to time
                // This is because the more mutations we have, the more different
                // of solutions we get, which takes more time.
                SWAP_TOTAL = ((int) (timeLimit * 0.10)) + 1;
                timerStart = System.nanoTime();

                // Timer for stoping the program
                Timer timer = new Timer();
                timer.schedule((new TimerTask() {
                    @Override
                    public void run() {
                        printFirst = true;

                    }
                    // Uses 85% of alotted time for saftey purposes (in case of very low time inputs)
                }), (long) (timeLimit * 0.85));
                fromFile = args[0];
                env.fromFile(fromFile);
            }
        } else {
            printSynopsis();
        }

        // Outputs file
        out = fromFile + ".out";

        createShutdownHook();


        if (args.length > 1) { // using command-line arguments
            runCommandLineMode();
            killShutdownHook();
        } else { // using interactive mode
            runInteractiveMode();
            killShutdownHook();
        }
    }

    /**
     * Return the environment object.  One should return an environment object that
     * makes sense for YOUR solution to the problem: the environment could contain
     * all the object instances required for the domain (like people, rooms, etc),
     * as well as potential solutions and partial solutions.
     *
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
    protected void createShutdownHook() {
    }

    protected void killShutdownHook() {
    }

    /**
     * Run in "Command line mode", that is, batch mode.
     */
    protected void runCommandLineMode() {
        try {
            long timeLimit = Long.parseLong(args[1]);
            System.out.println("Performing search for " + timeLimit + "ms\n");
            try {
                doSearch(env, timeLimit);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Error: The 2nd argument must be a long integer.");
            printSynopsis();
            System.exit(-1);
        }
        printResults();
    }

    /**
     * Perform the actual search
     *
     * @param env       An Environment object.
     * @param timeLimit A time limit in milliseconds.
     * @throws cpsc433.Room.FullRoomException should never occur for debug purposes
     */
    protected void doSearch(Environment env, long timeLimit) throws Room.FullRoomException {

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
                printResults();
                System.exit(0);
            }

        }

        Random rand = new Random();

        // -------------------- Mutations loop ------------------
        while (1 == 1) {
            if (printFirst) {
                printFirst = false;
                printResults();

                System.exit(0);
            }

            // Sets the total score that we use to keep track of average to 0
            int totalScore = 0;
            // For each member, calculate the score, and save it.
            // Then mutate.
            for (int popIndex = 0; popIndex < POP_SIZE; popIndex++) {
                // Saving previous scores
                int tempScore = population[popIndex].score();
                populationScores[popIndex] = tempScore;
                totalScore += tempScore;

                // If this assignment is better, then save the assignment
                if (tempScore > bestScore) {
                    bestScore = tempScore;
                    bestAssignments = population[popIndex].copyAssignedRooms();


                    if (printAfter) {
                        printResults();
                    }

                }

                // Finds new scores
                population[popIndex].mutate(SWAP_TOTAL);
            }
            // Average
            int averageScore = totalScore / POP_SIZE;

            // Compares the scores and decides if we should be mutating (90% chance to keep if it is better than average)
            for (int popIndex = 0; popIndex < POP_SIZE; popIndex++) {

                int newScore = population[popIndex].score();
                // Checks to see if the original was better than the new mutation
                if (averageScore > newScore && rand.nextInt(100) > 10) {
                    // Rolls back if it loses the roll
                    population[popIndex].rollback();
                }
            }
        }

    }


    /**
     * Output function for printing the results of the run.
     */
    protected void printResults() {
        //long start = System.nanoTime();

        PrintWriter writer;
        try {
            writer = new PrintWriter(args[0] + ".out", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("Can't open file " + args[0] + ".out");
            return;
        }

        // Printing the specific assignments
        if (bestAssignments != null) {
            for (Room room : bestAssignments) {
                Person[] assignedPeople = room.getAssignedPeople();
                if (assignedPeople[0] != null) {
                    writer.println("assign-to(" + assignedPeople[0].getName() + ", " + room.getName() + ")");
                }
                if (assignedPeople[1] != null) {
                    writer.println("assign-to(" + assignedPeople[1].getName() + ", " + room.getName() + ")");
                }
            }

            writer.println();
            writer.println("// Score: " + bestScore);
        } else {
            writer.println("// No solution");
        }

        writer.close();
        if (bestAssignments != null) {
            for (Room room : bestAssignments) {
                Person[] assignedPeople = room.getAssignedPeople();
                if (assignedPeople[0] != null) {
                    System.out.println("assign-to(" + assignedPeople[0].getName() + ", " + room.getName() + ")");
                }
                if (assignedPeople[1] != null) {
                    System.out.println("assign-to(" + assignedPeople[1].getName() + ", " + room.getName() + ")");
                }
            }

            System.out.println();
            System.out.println("// Score: " + bestScore);
        } else {
            System.out.println("// No solution");
        }
    }

    // Allows for assertions to be made and evals to be made also
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
                if (s.equals("?") || s.equals("help")) {
                    s = "!help()";
                    System.out.println("> !help()");
                }
                if (s.length() > 0) {
                    if (s.charAt(0) == '!')
                        env.assert_(s.substring(1));
                    else
                        System.out.print(" --> " + env.eval(s));
                }
                System.out.print("\n> ");
            }
        } catch (Exception e) {
            System.err.println("exiting: " + e.toString());
        }
    }
}
