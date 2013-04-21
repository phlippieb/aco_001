package a2.ant;

import a2.data.Data;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An ant that remembers a number of data items at past positions.
 * @author phlippie
 */
public class DataMemoryAnt extends Ant {

    Queue <RememberedData> memory;
    int memorySize;

    public DataMemoryAnt (int x, int y, int memorySize) {
        super (x,y);
        this.memorySize = memorySize;
        this.memory = new ArrayBlockingQueue<RememberedData> (memorySize);
    }

    public DataMemoryAnt (DataMemoryAnt copy) {
        super (copy);
    }

    public void remember (int x, int y, Data d) {
        if (memory.size() == memorySize) {
            memory.remove();
        }
        memory.add(new RememberedData (x, y, d));
    }

    public RememberedData [] getMemory () {
        return (RememberedData[])(this.memory.toArray());
    }
    

}
