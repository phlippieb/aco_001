package a2.grid;

import a2.data.Data;
/**
 * A cell in a grid.
 * The cell knows its own position and contents.
 * Should it know its grid?
 * @author phlippie
 */
public class Cell {
    int x, y;
    Data contents;

    public Cell (int x, int y) {
        this.x = x;
        this.y = y;
        this.contents = null;
    }

    public Cell (int x, int y, Data d) {
        this(x,y);
        this.contents = d;
    }

    public Cell (Cell copy) {
        this(copy.x, copy.y, copy.contents);
    }

    public void setContents (Data d) {
        this.contents = d;
    }

    public Data getContents () {
        return this.contents;
    }

    public void clearContents () {
        this.contents = null;
    }

    public boolean hasContents () {
        return this.contents != null;
    }

}
