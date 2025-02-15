package duke.Data;

import duke.Module.TimeSlot;
import duke.sports.MyClass;
import duke.sports.MyPlan;
import duke.sports.MyStudent;
import duke.sports.MyTraining;
import duke.Task.*;
import duke.sports.MyStudent;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Storage handles all the loading and saving of data
 * from and into the respective text files.
 */
public class Storage {
    private String filePath;
    private Scanner fileInput;

    public Storage(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
    }

    /**
     * This function saves the newly created task into duke.txt.
     * @param type The type of task created
     * @param e    The task created to be saved
     * @param date The date of the task created
     * @throws IOException io
     */
    public void saveFile(String type, Item e, String date) {
        try {
            if (type.equals("T")) {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "-" + e.getDuration() + "\n");
                fileWriter.close();
            } else if (type.equals("C")) {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-" + e.getInfo() + "-" + date + "\n");
                fileWriter.close();
            } else {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(type + "-" + e.checkStatus() + "-"
                        + e.getInfo() + "-" + e.getRawDate() + "\n");
                fileWriter.close();
            }
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    public String dateRevert(String date) {
        try {
            Date newDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date);
            String oldDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm").format(newDateFormat);
            return oldDateFormat;
        } catch (ParseException pe) {
            System.err.println("Error: Date in wrong format");
            return date;
        }
    }

    /**
     * This function parses the info of the duke.txt into an ArrayList.
     * @return ArrayList containing all the parsed data from the duke.txt file
     * @throws FileNotFoundException          e
     * @throws ArrayIndexOutOfBoundsException e
     */
    public ArrayList<Item> loadFile() {
        try {
            ArrayList<Item> list = new ArrayList<>();
            while (fileInput.hasNextLine()) { //do something
                String type;
                Boolean stat;
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                type = data[0];
                stat = (data[1].equals("1"));

                switch (type) {
                    case "D":
                        Item deadline = new Deadline(data[2], stat, dateRevert(data[3]));
                        list.add(deadline);
                        break;

                    case "E":
                        Item event = new Event(data[2], stat, dateRevert(data[3]));
                        list.add(event);
                        break;

                    case "T":
                        Item todo = new ToDo(data[2], stat, data[3]);
                        list.add(todo);
                        break;

                    case "A":
                        Item after = new After(data[2], stat, dateRevert(data[3]));
                        list.add(after);
                        break;

                    case "C":
                        Item myClass = new MyClass(data[2], stat, data[3]);
                        list.add(myClass);
                        break;

                    default:
                        System.out.println("No data");
                }
            }
            fileInput.close();
            return list;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * This function updates the list of tasks.
     * Erases the entire list that exists presently and rewrites the file.
     * @param up The updated ArrayList that must be used to recreate the updated duke.txt
     * @throws IOException io
     */
    public void updateFile(ArrayList<Item> up) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (Item i : up) {
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.write(i.getType() + "-" + i.checkStatus() + "-" + i.getInfo() + "-" + i.getRawDate() + "\n");
                fileWriter.close();
            } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
    }

    /**
     * Reads filePath, takes in Strings and turns them into a list of TimeSlot objects
     */
    public ArrayList<TimeSlot> loadSchedule() throws ParseException {
        try {
            ArrayList<TimeSlot> temp = new ArrayList<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
                Date date1 = simpleDateFormat.parse(data[1]);
                Date date2 = simpleDateFormat.parse(data[2]);
                TimeSlot t = new TimeSlot(date1, date2, data[3], data[0]);
                temp.add(t);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function saves the newly created TimeSlot into timeslots.txt
     * @param t The TimeSlot object created to be saved
     */
    public void saveSchedule(TimeSlot t) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat("HHmm");
            fileWriter.write(t.getClassName() + "-" + df.format(t.getStartTime()) + "-" + df.format(t.getEndTime()) + "-" + t.getLocation() + "\n");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    /**
     * This function updates the list of tasks.
     * @param up The updated ArrayList that must be used to recreate the updated timeslots.txt
     */
    public void updateSchedule(ArrayList<TimeSlot> up) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        for (TimeSlot t : up) {
            try {
                FileWriter fileWriter = new FileWriter(filePath, true);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HHmm");
                fileWriter.write(t.getClassName() + "-" + df.format(t.getStartTime()) + "-" + df.format(t.getEndTime()) + "-" + t.getLocation() + "\n");
                fileWriter.close();
            } catch (IOException io) {
                System.out.println("File not found:" + io.getMessage());
            }
        }
    }

    /**
     * Reads filePath, takes in Strings and turns them into a hash map of goals.
     * @return A hash map of goals.
     * @throws ParseException if the user input is in wrong format.
     */
    public Map<Date,ArrayList<String>> loadGoal() throws ParseException {
        try {
            Map<Date,ArrayList<String>> temp = new HashMap<>();
            while (fileInput.hasNextLine()) {
                String s1 = fileInput.nextLine();
                String[] data = s1.split("-");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = simpleDateFormat.parse(data[0]);
                ArrayList<String> temp2 = new ArrayList<>();
                for (String str : data) {
                    if (!str.equals(data[0])) {
                        temp2.add(str);
                    }
                }
                temp.put(date,temp2);
            }
            fileInput.close();
            return temp;
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * This function updates the hash map of goals.
     * Erases the entire hash map that exists presently and rewrites the file.
     * @param goals The updated hash map that must be used to recreate the updated goals.txt
     * @throws IOException io if the file cannot be found.
     */
    public void updateGoal(Map<Date,ArrayList<String>> goals) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            for (Map.Entry<Date,ArrayList<String>> entry : goals.entrySet()) {
                String extra = "";
                ArrayList<String> temp = entry.getValue();
                for (String str : temp) {
                    extra += "-" + str;
                }
                fileWriter.write(df.format(entry.getKey()) + extra + "\n");
            }
            fileWriter.close();
        } catch (IOException io) {
            System.out.println("File not found:" + io.getMessage());
        }
    }

    public int loadPMap (Map<Integer, ArrayList<MyTraining>> map) throws FileNotFoundException {
        MyPlan plan = new MyPlan();
        ArrayList<MyTraining> list = new ArrayList<>();
        int division = Integer.parseInt(fileInput.nextLine().split(": ")[1]);

        int index = 1;
        int intensity = 1;
        int plan_num = 0;

        while (fileInput.hasNextLine()) {
            String in = fileInput.nextLine();
            if (in.contains("Intensity") || in.contains("Plan")) {
                String[] line = in.split(": ");
                if (line[1].equals("high")) {
                    MyPlan.Intensity x = MyPlan.Intensity.high;
                    intensity = x.getValue();
                } else if (line[1].equals("moderate")) {
                    MyPlan.Intensity x = MyPlan.Intensity.moderate;
                    intensity = x.getValue();
                } else if (line[1].equals("relaxed")) {
                    MyPlan.Intensity x = MyPlan.Intensity.relaxed;
                    intensity = x.getValue();
                } else {
                    MyPlan.Intensity y = MyPlan.Intensity.valueOf(intensity);
                    int key = plan.createKey(y.name(), plan_num);
                    map.put(key,list);
                    list.clear();
                    plan_num = Integer.parseInt(line[1]);
                }
            } else {
                if (!in.equals("")) {
                    String[] line = in.split(" \\| ");
                    MyTraining ac = new MyTraining(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    list.add(ac);
                }
            }
            if (!fileInput.hasNextLine()) {
                MyPlan.Intensity y = MyPlan.Intensity.valueOf(intensity);
                int key = plan.createKey(y.name(), plan_num);
                map.put(key,list);
            }
            index++;
        }
        return division;
    }

    public void savePMap() {
        System.out.println("To be confirmed");
    }

    public void saveToFile(ArrayList<MyStudent> saveName) {
        File file = new File(".\\\\src\\\\main\\\\java\\\\duke\\\\Data\\\\studentList.txt"); // Creating the textfile
        try {
            PrintWriter output = new PrintWriter(file);
            for (MyStudent x : saveName) {
                output.println(x.toString());
            }
            output.close();
        } catch (IOException ex) {
            System.out.print("ERROR: Not Available");
        }
    }


    private Formatter x;

    public void openStudentListFile() {
        try {
            x = new Formatter("studentList.txt");

        } catch (Exception e){
            System.out.println("No such files");
        }
    }

    public void updateStudentList() {
        x.format("Student Name, student age, Student address");
    }

    public void closeFile() {
        x.close();
    }

}
