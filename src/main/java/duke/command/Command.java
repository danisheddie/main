package duke.command;

import duke.view.CliView;
import duke.data.Storage;
import duke.models.Schedule;
import duke.models.manageStudents.ManageStudents;
import duke.models.MyPlan;
import duke.task.TaskList;

/**
 * Represents a Command class which will be inherited
 * by specific *Command subclasses
 * to run their specific execute functions.
 */
public abstract class Command {
    /**
     * Ui object which will be shared by all subclasses.
     */
    private CliView cliView;
    /**
     * The exit status of the program.
     */
    private boolean exit = false;

    /**
     * Getter function for the exit status of the program.
     *
     * @return exit the program.
     */
    public boolean isExit() {
        return this.exit;
    }

    /**
     * Function to make the exit become true.
     */
    public void makeExitTrue() {
        this.exit = true;
    }

    /**
     * The execute method which will be shared by all subclasses.
     *
     * @param tasks         The ArrayList of Task objects.
     * @param sharedCLIView The Ui object to manage user interface to user.
     * @param storage       The Storage object to save and load user's tasks.
     * @param schedule      The Schedule object to store classes in time slots.
     * @param students      The ManageStudents object to
     *                      manage students in classes.
     * @param plan          The MyPlan object to manage the training plans.
     */
    public abstract void execute(TaskList tasks,
                                 CliView sharedCLIView,
                                 Storage storage,
                                 Schedule schedule,
                                 ManageStudents students,
                                 MyPlan plan);
}
