package a2.ant;

import a2.data.Data;

/**
 * A single memory of data at a certain position.
 * @author phlippie
 */
public class RememberedData implements Cloneable{
    public int x, y;
    public Data d;

    public RememberedData (int x, int y, Data d) {
        this.x = x; this.y = y; this.d = d;
    }

    @Override
    public RememberedData clone () {
        return new RememberedData (x, y, d);
    }
}
