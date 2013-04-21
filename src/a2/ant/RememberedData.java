package a2.ant;

import a2.data.Data;

/**
 * A single memory of data at a certain position.
 * @author phlippie
 */
public class RememberedData {
    int x, y;
    Data d;

    public RememberedData (int x, int y, Data d) {
        this.x = x; this.y = y; this.d = d;
    }
}
