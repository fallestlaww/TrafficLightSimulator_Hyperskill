package traffic.modules;

import static traffic.modules.Menu.SCANNER;

public class Initializer {
    /**
     * Function for initializing values for count of roads claiming only integers with
     * error handling in case of input negative integers, strings, etc
     * @return the number of roads
     */
    static int setRoadNumber() {
        System.out.println("Input the number of roads: ");
        while (true) {
            String input = SCANNER.nextLine();
            if (ValidInput.getValidInput(input)) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Incorrect input. Please try again");
            }
        }
    }

    /**
     * Function for initializing values for interval claiming only integers with
     * error handling in case of input negative integers, strings, etc
     * @return the interval
     */
    static int setInterval() {
        System.out.println("Input the interval: ");
        while (true) {
            String input = SCANNER.nextLine();
            if (ValidInput.getValidInput(input)) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Incorrect input. Please try again");
            }
        }
    }
}
