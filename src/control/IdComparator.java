package control;

import java.util.Comparator;
import model.Worker;
public class IdComparator implements Comparator<Worker> {
    @Override
    public int compare(Worker w1, Worker w2) {
        if (w1.getId() > w2.getId()) {
            return 1;
        } else if (w1.getId() < w2.getId()) {
            return -1;
        } else {
            return 0;
        }
    }

}