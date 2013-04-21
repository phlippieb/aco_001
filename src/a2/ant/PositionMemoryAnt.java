package a2.ant;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * An ant that remembers the density calculated at a number of previous positions.
 * @author phlippie
 */
public class PositionMemoryAnt extends Ant {

    Queue <RememberedPosition> memory;
    int memorySize;

    public PositionMemoryAnt (int x, int y, int memorySize) {
        super (x,y);
        this.memorySize = memorySize;
        this.memory = new ArrayBlockingQueue<RememberedPosition> (memorySize);
    }

    public PositionMemoryAnt (DataMemoryAnt copy) {
        super (copy);
    }

    public void remember (int x, int y) {
        if (memory.size() == memorySize) {
            memory.remove();
        }
        memory.add(new RememberedPosition (x, y));
    }

    public RememberedPosition [] getMemory () {
        //return (RememberedPosition[])(this.memory.toArray());
        return this.memory.toArray(new RememberedPosition [0]);
    }

    public void clearMemory () {
        memory.clear();
    }

    public boolean hasMemory () {
        return (this.memory != null && this.memory.size() > 0);
    }
}
