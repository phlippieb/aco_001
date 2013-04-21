package a2.data;

/**
 *
 * @author phlippie
 */
public class EuclideanDistanceDataComparator implements DataComparator {

    public double getDistanceBetween (Data d1, Data d2) throws RuntimeException {

        if (d1.getSize () != d2.getSize()) {
            throw new RuntimeException ("Data vectors have different sizes");
        }

        double distance = 0.0;
        for (int i = 0; i < d1.getSize(); i++) {
            distance += Math.pow( (Double)d1.getData().get(i) - (Double)d2.getData().get(i), 2.0 );
        }

        return distance;
    }
}
