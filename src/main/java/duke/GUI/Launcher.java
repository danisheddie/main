package duke.GUI;

import duke.Duke;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {

    public Launcher() {
    }

    /**
     * Begin GUI application and run main duke class.
     * @param args expects array of string objects
     */
    public static void main(String[] args) {
        Application.launch(Duke.class, args);
    }
}
