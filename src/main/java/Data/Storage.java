package Data;


import Task.*;
import javafx.concurrent.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Storage handles all the loading and saving of data from and into the duke.txt file respectively
 */
public class Storage {
    private static String path = ".\\src\\main\\java\\Data\\duke.txt";
    private static Scanner fileInput;
    private static ArrayList<item> oldList = new ArrayList<>();
    private static File f = new File(path);

    private static ArrayList<item> list = new ArrayList<>();


    /**
     * This function parses the info of the duke.txt into an ArrayList.
     *
     * @return ArrayList containing all the parsed data from the duke.txt file
     * @throws FileNotFoundException e
     * @throws ArrayIndexOutOfBoundsException e
     */
    public static ArrayList<item> loadFile() {

        try {
            fileInput = new Scanner(f);
            while (fileInput.hasNextLine()) { //do something
                String type, info;
                Boolean stat;
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                type = data[0];
                stat = (data[1].equals("1"));

                switch (type) {
                    case "D":
                        item deadline = new Deadline(data[2], stat, TaskList.dateRevert(data[3]));
                        oldList.add(deadline);
                        break;

                    case "E":
                        item event = new Event(data[2], stat, TaskList.dateRevert(data[3]));
                        oldList.add(event);
                        break;

                    case "T":
                        item todo = new ToDo(data[2], stat, TaskList.dateRevert(data[3]));
                        oldList.add(todo);
                        break;

                    case "A":
                        item after = new After(data[2], stat, TaskList.dateRevert(data[3]));
                        oldList.add(after);
                        break;
                    
                     default:
                        System.out.println("No data");
                }
            }
            fileInput.close();
            return oldList;
        }
        catch (FileNotFoundException | ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function saves the newly created task into duke.txt
     *
     * @param type The type of task created
     * @param e The task created to be saved
     * @param date The date of the task created
     * @throws  IOException io
     */
    public static void saveFile(String type, item e, String date) {
        try {
            if (type.equals("T")) {
                FileWriter fileWriter = new FileWriter(f, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "\n");
                fileWriter.close();
            }
            else  {
                FileWriter fileWriter = new FileWriter(f, true);

                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "-" + e.getRawDate() + "\n");
                fileWriter.close();
            }
        }
        catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * This function updates the list of tasks
     * Erases the entire list that exists presently and rewrites the file
     *
     * @param up The updated ArrayList that must be used to recreate the updated duke.txt
     * @throws  IOException io
     */
    public static void updateFile(ArrayList<item> up) {
        try {
            FileWriter fileWriter = new FileWriter(f);
            fileWriter.write("");
            fileWriter.close();
        }
        catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (item i: up) {
            try {
                FileWriter fileWriter = new FileWriter(f,true);
                fileWriter.write(i.getType() + "-" + i.checkStatus() + "-" + i.getInfo() + "-" +i.getRawDate()+ "\n");
                fileWriter.close();
            }
            catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
    }
}
