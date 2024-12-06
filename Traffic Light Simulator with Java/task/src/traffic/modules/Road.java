package traffic.modules;

import java.util.Deque;

import static traffic.modules.Menu.*;
import static traffic.modules.Updater.updateIntervals;

public class Road {
    private String roadName;
    private int interval = 0;
    protected int openInterval = 0;
    protected int closeInterval = 0;
    private boolean open = false;

    /**
     * Function for adding road right in the program by choosing option 1.
     * @param roads deque of added roads to work with
     * @param roadsCount max count of added roads
     * @param interval interval for the roads
     */
    protected static void addRoad(Deque<Road> roads, int roadsCount, int interval) {
        System.out.println("Input road name: ");
        String input = SCANNER.nextLine();
        /*
         if size of deque of roads is lower than count of roads,
         specified at the beginning of the program execution,
         then we can add new road to the deque
         */
        if (roads.size() < roadsCount) {
            Road road = new Road(input);
            roads.addLast(road);
            System.out.println(input + " added");

            updateIntervals(roads, interval); // updating the intervals after adding new road to work correctly
        } else {
            System.out.println("Roads queue is full");
        }
    }

    /**
     * Function for removing the road right in the program.
     * @param roads deque of added roads to work with
     * @param interval interval for the roads
     */
    protected static void deleteRoad(Deque<Road> roads, int interval) {
        if (!roads.isEmpty()) {
            // according to the requirements of the project, this function must remove only first road
            Road removedRoad = roads.removeFirst();
            System.out.println(removedRoad.getRoadName() + " deleted");

            updateIntervals(roads, interval); // updating the intervals after removing a road to work correctly
        } else {
            System.out.println("Roads queue is empty");
        }
    }

    /**
     * Function for determining states of roads and their information output
     * @param roads deque of added roads to work with
     */
    protected static void printRoads(Deque<Road> roads) {
        System.out.println();
        for (Road road : roads) {
            String stateColor = road.isOpen() ? ANSI_GREEN : ANSI_RED;
            String stateText = road.isOpen() ? "open" : "closed";
            int time = road.isOpen() ? road.getOpenInterval() : road.getCloseInterval();
            System.out.printf("%sRoad \"%s\" is %s for %ds.%s\n",
                    stateColor, road.getRoadName(), stateText, time, ANSI_RESET);
        }
        System.out.println();
    }

    public boolean isOpen() {
        return open;
    }
    public  void setOpen(boolean open) {
        this.open = open;
    }

    public int getOpenInterval() {
        return openInterval;
    }

    public void setOpenInterval(int openInterval) {
        this.openInterval = openInterval;
    }

    public int getCloseInterval() {
        return closeInterval;
    }

    public void setCloseInterval(int closeInterval) {
        this.closeInterval = closeInterval;
    }

    public Road(String roadName) {
        this.roadName = roadName;
    }

    public String getRoadName() {
        return roadName;
    }

    @Override
    public String toString() {
        return roadName + " " + interval;
    }
}
