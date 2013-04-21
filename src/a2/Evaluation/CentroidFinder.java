package a2.Evaluation;

import a2.data.Data;
import a2.grid.Cell;
import java.util.ArrayList;

/**
 * A tool to find the centroid (data-wise) of a cluster.
 * @author phlippie
 */
public class CentroidFinder {
    public static Data find (ArrayList<Cell> cluster, int dataSize) {

        double centroidData = 0.0;
        ArrayList<Double> dataArrayList = new ArrayList<Double>();
        for (int j = 0; j < dataSize; j++) {
            centroidData = 0.0;
            for (int i = 0; i < cluster.size(); i++) {
                centroidData += (Double)(cluster.get(i).getData().getData().get(j));
            }
            centroidData /= cluster.size();
            dataArrayList.add(centroidData);
        }

        return new Data(dataArrayList);
    }
}
