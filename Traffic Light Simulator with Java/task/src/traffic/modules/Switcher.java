package traffic.modules;

import traffic.enums.States;
import static traffic.modules.Menu.*;

public class Switcher {
    private static volatile States state = States.NOT_STARTED;
    /**
     * Function which claim the "Enter" key to stop the thread`s output by changing state to "menu"
     */
    protected static void switchToSystemState() {
        while (getState() == States.SYSTEM) {
            String input = SCANNER.nextLine();
            if (input.isEmpty()) {
                setState(States.MENU);
            }
        }
    }

    public static States getState() {
        return state;
    }

    public static void setState(States state) {
        Switcher.state = state;
    }
}
