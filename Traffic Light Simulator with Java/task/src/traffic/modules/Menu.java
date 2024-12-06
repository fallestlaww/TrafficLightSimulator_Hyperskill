package traffic.modules;

import traffic.enums.States;
import java.util.*;
import static traffic.modules.Cleaner.clearConsole;
import static traffic.modules.Initializer.setInterval;
import static traffic.modules.Initializer.setRoadNumber;
import static traffic.modules.Road.*;
import static traffic.modules.Switcher.*;
import static traffic.modules.Updater.updateRoadStates;

public class Menu implements Runnable {
    private static final String greeting = "Welcome to the traffic management system!";
    protected static final Scanner SCANNER = new Scanner(System.in);
    private static final long START_TIME = System.currentTimeMillis();
    private static int roadsCount;
    private static int interval;
    private static Deque<Road> roads = new ArrayDeque<>();
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_RESET = "\u001B[0m";

    /**
     * Function for showing main menu in which user will work
     */
    public static void showMenu() {
        System.out.println(greeting);
        setState(States.MENU);
        initialize();

        // Create and start the system thread
        Thread thread = new Thread(new Menu());
        thread.setName("QueueThread");
        thread.start();

        menuLogic(thread); // main menu logic
    }

    /**
     * Function with main menu text which contains the options user can choose
     */
    private static void menuMessage() {
        System.out.print("""
                Menu:
                1. Add road
                2. Delete road
                3. Open system
                0. Quit
                """);
    }

    /**
     * Function contains the logic of the program according
     * @param thread transmits the thread created in {@link #showMenu()} which will be working in parallel with the program, but
     *               output will be shown only after choosing option "3"
     *
     */
    private static void menuLogic(Thread thread) {
        while (true) {
            clearConsole();
            menuMessage();
            String option = SCANNER.nextLine();
            switch (option) {
                case "1" -> {
                    addRoad(roads, roadsCount, interval);
                    SCANNER.nextLine(); // according to requirements of the project, this scanner called to claim the "Enter" key for continuation of work
                }
                case "2" -> {
                    deleteRoad(roads, interval);
                    SCANNER.nextLine(); // according to requirements of the project, this scanner called to claim the "Enter" key for continuation of work
                }
                case "3" -> {
                    setState(States.SYSTEM); // switching the state to call the output of thread, working parallel to the program
                    switchToSystemState();
                }
                case "0" -> {
                    System.out.println("Bye!");
                    thread.stop(); // better to use thread.interrupt(), but in this project interrupt calls an error in test
                    System.exit(0); //safe ending of the program
                }
                default -> {
                    System.out.println("Incorrect option");
                    SCANNER.nextLine(); // according to requirements of the project, this scanner called to claim the "Enter" key for continuation of work
                }
            }
        }
    }

    /**
     * Function for initializing values for {@link #interval} and {@link #roadsCount}
     */
    private static void initialize() {
      roadsCount =  setRoadNumber();
      interval = setInterval();
    }

    /**
     * Function for working with thread QueueThread created in {@link #showMenu()}
     */
    @Override
    public void run() {
        while (true) {
            /*
            according to the project requirements thread can
            output some information only if state equals "system"
             */

            if (getState() == States.SYSTEM) {
                try {
                    long elapsedTime = (System.currentTimeMillis() - START_TIME) / 1000;
                    clearConsole();
                    System.out.printf("""
                ! %ds. have passed since system startup !
                ! Number of roads: %d !
                ! Interval: %d !
                """, elapsedTime, roadsCount, interval);

                    if (!roads.isEmpty()) {
                        // updating the road conditions
                        updateRoadStates(elapsedTime, roads, interval);
                        // output current conditions of roads
                        printRoads(roads);
                    }

                    System.out.println("! Press \"Enter\" to open menu !");
                    Thread.sleep(1000); // 1 second pause
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}