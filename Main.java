package OS_projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Random rand = new Random();
        final int pageLimit = 100;
        final int referenceStringSize = 100;
        final int memorySize = rand.nextInt(20) + 1;

        System.out.println("***Welcome to the page replacement algorithms simulator***\n");

        List<Integer> list = new ArrayList<Integer>(referenceStringSize);

        for (int i = 0; i < referenceStringSize; i++) {
            list.add(rand.nextInt(pageLimit));
        }

        Memory m = new Memory(memorySize, list);
        while(true){
            readChoice(m);
            System.out.println("\n\n");
        }
    }

    private static void printOptions(){
        System.out.println("the program implements the following 6 algorithms:\n" +
                "1) FIFO\n" +
                "2) Second Chance\n" +
                "3) Enhanced Second Chance\n" +
                "4) Optimal\n" +
                "5) Least Recently used\n" +
                "6) Least Frequenlty used\n" +
                "please enter the number of the algorithm you want to simulate:");
    }

    private static void readChoice(Memory m){
        printOptions();
        int c = new Scanner(System.in).nextInt();
        switch(c){
            case 1:
                m.FIFO();
                break;
            case 2:
                m.SC();
                break;
            case 3:
                m.ESC();
                break;
            case 4:
                m.Optimal();
                break;
            case 5:
                m.LRU();
                break;
            case 6:
                m.LFU();
                break;
            default:
                System.exit(0);

        }
    }
}
