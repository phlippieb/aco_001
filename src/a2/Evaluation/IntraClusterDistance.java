package a2.Evaluation;

import a2.data.Data;
import a2.data.EuclideanDistanceDataComparator;
import a2.grid.Cell;
import java.util.ArrayList;

/**
 *
 * @author phlippie
 */
public class IntraClusterDistance {

    public static double find (ArrayList<Cell> cluster, int dataSize) {
        Data centroid = CentroidFinder.find(cluster, dataSize);
        double distance = 0.0;
        for (Cell c : cluster) {
            distance += (Double)(new EuclideanDistanceDataComparator().getDistanceBetween(centroid, c.getData()));
        }

        return distance / cluster.size();
        
    }
}
