package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.BLUE;
import static pl.coderslab.ConsoleColors.RESET;

public class Main {
    static String[] optionsMenu = {"add", "remove", "list", "exit"};
    static String[][] tasks;
    static String fileMain ="tasks.csv";

    public static void main(String[] args) {
        tasks = arrayDataTab(fileMain);
        menuStart(optionsMenu);

        Scanner scanner = new Scanner(System.in);
        boolean finish = false;

        while (!finish) {
            String input = scanner.nextLine();
            switch (input) {
                case "exit":
                    saveTabToFile(fileMain, tasks);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    finish = true;
                    break;
                case "add":
                    addTask();
                    break;
                    case "remove":
                    try {
                        removeNumberTask(taskRemoveNumber());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Cannot remove task: " + e.getMessage());
                    }
                    break;
                case "list":
                    printTab(tasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            if (!finish) {
                menuStart(optionsMenu);
            }
        }
    }
        public static void menuStart (String[]tab){
            System.out.println(BLUE + "Please select an options." + RESET);
            for (String options : tab) {
                System.out.println(options);
            }
        }
        public static String[][] arrayDataTab (String file){

            Path dir = Paths.get(fileMain);
            if (!Files.exists(dir)) {
                System.out.println("File not exist.");
                System.exit(0);
            }
            String[][] tab = null;
            try {
                List<String> strings = Files.readAllLines(dir);
                tab = new String[strings.size()][3];

                for (int i = 0; i < strings.size(); i++) {
                    String[] split = strings.get(i).split(",");
                    for (int j = 0; j < 3; j++) {
                        tab[i][j] = split[j].strip();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return tab;
        }
        public static void addTask () {
            System.out.println("Plase add task description");
            Scanner scanner = new Scanner(System.in);
            String newTopic = scanner.nextLine();
            System.out.println("Plase add date YYYY-MM-DD"); //warunki
            String dateTopic = scanner.nextLine();
            System.out.println("Is your task important: true/false");
            String importantTopic = scanner.nextLine();


            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length - 1] = new String[3];
            tasks[tasks.length - 1][0] = newTopic;
            tasks[tasks.length - 1][1] = dateTopic;
            tasks[tasks.length - 1][2] = importantTopic;

            System.out.println(newTopic + " , " + dateTopic + " , " + importantTopic);
            System.out.println("Your task is typed.");
        }
        public static void removeNumberTask ( int num){
            //taskRemoveNumber();

            if (num < 0) {
                System.out.println("Enter number greater or equal 0");
            } else if (num < tasks.length) {
                tasks = ArrayUtils.remove(tasks, num);
                System.out.println("Task is successfully deleted.");
            } else {
                System.out.println("There are not so many tasks. Enter number greater or equal 0");
            }

        }
        public static int taskRemoveNumber () {
            System.out.println("Enter the task number to be deleted: ");
            Scanner scanner = new Scanner(System.in);
            String num = scanner.nextLine();
            return Integer.parseInt(num);
        }
        public static void printTab (String[][]tab){

            for (int i = 0; i < tab.length; i++) {
                System.out.print((i + 1) + " : ");

                for (int j = 0; j < 3; j++) {
                    System.out.print(tab[i][j] + " ");
                }
                System.out.println();
            }
        }
        public static void saveTabToFile(String fileName, String[][] tab) {
        Path dir = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}


