package traffic.modules;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Updater {
    /**
     * Function for monitoring road conditions and setting initial intervals
     * the main difference between it and {@link #updateIntervals(Deque, int)} is that {@link #updateIntervals(Deque, int)} controls the newly introduced roads after the flow output is triggered,
     * while this function controls the initial introduced roads
     * @param elapsedTime transmits the running time of the program, which is calculated in run method of the thread.
     *  @param roads deque of added roads to work with
     *  @param interval interval for the roads
     */
    protected static void updateRoadStates(long elapsedTime, Deque<Road> roads, int interval) {
        int size = roads.size();

        if (elapsedTime > 0) {
            for (int i = 0; i < size; i++) {
                Road road = roads.pollFirst(); // get the first element of deque

                if (size == 1) {
                    // if the road is one, it is always open
                    road.setOpen(true);
                    road.setOpenInterval(road.getOpenInterval() - 1);

                    if (road.getOpenInterval() <= 0) {
                        // restart the countdown for a single road
                        road.setOpenInterval(interval);
                    }
                } else if (road.isOpen()) {
                    // if the road is open, we reduce its open interval
                    road.setOpenInterval(road.getOpenInterval() - 1);
                    if (road.getOpenInterval() <= 0) {
                        // close the road after the end of the interval
                        road.setOpen(false);
                        road.setCloseInterval(interval * (size - 1)); // adding closed interval
                    }
                } else {
                    // if the road is closed, reduce its closed interval
                    road.setCloseInterval(road.getCloseInterval() - 1);
                    if (road.getCloseInterval() <= 0) {
                        // open the road after the end of the closed interval
                        road.setOpen(true);
                        road.setOpenInterval(interval);
                    }
                }

                roads.offerLast(road); // adding the road in the end of deque
            }
        }
    }

    /**
     * Function for updating intervals of opening/closing roads. Useful as in case of adding/removing roads before starting the system state
     * as in case of adding/removing roads after starting the system state
     * @param roads deque of added roads to work with
     * @param interval interval for the roads
     */
    protected static void updateIntervals(Deque<Road> roads, int interval) {
        //if there are no roads in deque, program will ignore this function
        if (roads.isEmpty()) {
            return;
        }

        int size = roads.size();
        List<Road> tempList = new ArrayList<>(roads); //copying our data to new temporary list working only in this function
        roads.clear(); // clearing the deque for further filling with new data

        for (int i = 0; i < size; i++) {
            Road road = tempList.get(i);
            // if the road is only one
            if (i == 0) {
                // the first road is always open for the given interval
                road.setOpen(true);
                road.setOpenInterval(interval);
                road.setCloseInterval(0); // no interval of closing according to requirements of project
            } else if (i == 1) {
                // the second road begins closed
                road.setOpen(false);
                road.setOpenInterval(0); // won`t be open before first road will be closed
                road.setCloseInterval(interval); // interval of closing
            } else {
                // for third and other roads
                /*
                logic for calculation of interval with which roads will be open properly
                by "properly open" I mean the road won't open until others are closed,
                 the road won't close when others are closed, etc.
                 */
                int previousOpenTime = tempList.get(i - 1).getOpenInterval();

                road.setOpen(false); // begins closed
                road.setOpenInterval(interval * (i) - (interval - previousOpenTime)); // interval to opening
                road.setCloseInterval(interval * (i)); // interval to closing
            }

            roads.offerLast(road); // adding the road to deque
        }
    }
}
