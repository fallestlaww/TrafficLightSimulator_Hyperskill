package traffic.modules;

import java.io.IOException;

public class Cleaner {
    /**
     * Function for cleaning the output in console.
     * ATTENTION! This function may not work in your IDE.
     */
    protected static void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        }
        catch (IOException | InterruptedException e) {}
    }
}
