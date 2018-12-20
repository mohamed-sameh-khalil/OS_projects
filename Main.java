package OS_projects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {


        List list = new ArrayList(100);
        for (int i = 0; i < 100; i++) {
            Random rand = new Random();
            list.add(rand.nextInt(20) + 1);
        }
        Memory m = new Memory(5, list);
        m.FIFO();
        m.Optimal();
    }
}
