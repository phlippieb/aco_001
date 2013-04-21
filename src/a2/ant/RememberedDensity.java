package a2.ant;

/**
 * A single memory of the density at a certain position.
 * @author phlippie
 */
public class RememberedDensity {
    int x, y;
    double d;

    public RememberedDensity (int x, int y, Double d) {
        this.x = x; this.y = y; this.d = d;
    }
}
