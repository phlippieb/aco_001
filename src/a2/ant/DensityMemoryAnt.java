package a2.ant;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An ant that remembers the density calculated at a number of previous positions.
 * @author phlippie
 */
public class DensityMemoryAnt extends Ant {

    Queue <RememberedDensity> memory;
    int memorySize;

    public DensityMemoryAnt (int x, int y, int memorySize) {
        super (x,y);
        this.memorySize = memorySize;
        this.memory = new ArrayBlockingQueue<RememberedDensity> (memorySize);
    }

    public DensityMemoryAnt (DataMemoryAnt copy) {
        super (copy);
    }

    public void remember (int x, int y, double d) {
        if (memory.size() == memorySize) {
            memory.remove();
        }
        memory.add(new RememberedDensity (x, y, d));
    }

    public RememberedDensity [] getMemory () {
        return (RememberedDensity[])(this.memory.toArray());
    }


}
