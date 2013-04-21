package a2.ant;

/**
 * A single memory of the density at a certain position.
 * @author phlippie
 */
public class RememberedPosition implements Cloneable {
    public int x, y;

    public RememberedPosition (int x, int y) {
        this.x = x; this.y = y;
    }

    @Override
    public RememberedPosition clone () {
        return new RememberedPosition(x, y);
    }
}
