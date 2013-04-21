package a2.data;

import java.util.ArrayList;

/**
 * Abstract class to define basic Data behaviour.
 * The distance calculation to another data vector is to be implemented in subclasses.
 * @author phlippie
 */
//public abstract class Data implements Cloneable {
public class Data implements Cloneable {
    ArrayList data;
    int x,y;
    public Data (ArrayList d) {
        this.data = (ArrayList)(d.clone());
    }

    public Data (Data copy) {
        this.data = (ArrayList)(copy.data.clone());
    }

    @Override
    public Data clone () {
        return new Data ((ArrayList)(this.data.clone()));
    }

    public ArrayList getData () {
        return (ArrayList)(this.data.clone());
    }

    public void setData (ArrayList d) {
        this.data = (ArrayList)(d.clone());
    }

    public int getSize () {
        return this.data.size();
    }

    public void setPos(int x, int y) {
        this.x = x; this.y = y;
    }

    public int getX () { return this.x; }
    public int getY () { return this.y; }
}
