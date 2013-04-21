package a2.ant;

import a2.data.*;

/**
 * Class for basic ant properties
 * @author phlippie
 */
public class Ant {
    int x;
    int y;
    Data d;

    public Ant (int x, int y) {
        this.x = x;
        this.y = y;
        this.d = null;
    }

    public Ant (Ant copy) {
        this.x = copy.x;
        this.y = copy.y;
        if (copy.hasData()) {
            this.d = copy.getData().clone();
        } else {
            this.d = null;
        }
    }

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public void setData (Data d) {
        if (d == null){
            this.d = null;
        }
        this.d = d.clone();
    }

    public Data clearData () {
        Data data = this.d.clone();
        this.d = null;
        return data;
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public Data getData () {
        if (this.d == null) {
            return null;
        }
        return this.d.clone();
    }

    public boolean hasData () {
        return this.d != null;
    }
}
