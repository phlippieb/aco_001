package a2.grid;

import a2.ant.Ant;
import a2.data.Data;
/**
 * A cell in a grid.
 * The cell knows its own position and data.
 * Should it know its grid?
 * @author phlippie
 */
public class Cell {
    int x, y;
    Data data;
    Ant ant;


    public Cell (int x, int y) {
        this.x = x;
        this.y = y;
        this.data = null;
    }

    public Cell (int x, int y, Data d) {
        this(x,y);
        this.data = d;
    }

    public Cell (Cell copy) {
        this(copy.x, copy.y, copy.data);
    }

    public void setData (Data d) {
        this.data = d;
    }

    public Data getData () {
        return this.data;
    }

    public Data clearData () {
        Data d = this.data.clone();
        this.data = null;
        return d;
    }

    public boolean hasData () {
        return this.data != null;
    }

    public void setAnt (Ant a) {
        this.ant = a;
    }

    public Ant getAnt () {
        return this.ant;
    }

    public void clearAnt () {
        this.ant = null;
    }

    public boolean hasAnt () {
        return this.ant != null;
    }

    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

}
