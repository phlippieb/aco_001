package a2.data;

import java.util.ArrayList;

/**
 * Abstract class to define basic Data behaviour.
 * The distance calculation to another data vector is to be implemented in subclasses.
 * @author phlippie
 */
public abstract class Data implements Cloneable {
    ArrayList data;
    public Data (ArrayList d) {
        this.data = (ArrayList)(d.clone());
    }

    public Data (Data copy) {
        this.data = (ArrayList)(copy.data.clone());
    }

    public ArrayList getData () {
        return (ArrayList)(this.data.clone());
    }

    public void setData (ArrayList d) {
        this.data = (ArrayList)(d.clone());
    }

    public abstract double distanceTo (Data other);
    public abstract double distanceBetween (Data first, Data second);
}
