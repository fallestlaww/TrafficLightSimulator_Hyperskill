package traffic.modules;

public class ValidInput {
    /**
     * Function for checking input
     * @param prompt claim a string to check
     * @return boolean value true/false
     */
    protected static boolean getValidInput(String prompt) {
        return prompt.matches("\\d+") && Integer.parseInt(prompt) > 0;
    }
}
