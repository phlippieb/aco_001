package a2.Evaluation;

import a2.data.Data;
import a2.data.EuclideanDistanceDataComparator;
import a2.grid.Cell;
import java.util.ArrayList;

/**
 *
 * @author phlippie
 */
public class InterClusterDistance {

    /**
     *      * Screwit. This uses euclidean distance always.
     * Find the centroids of the clusters and return the distance between their data.
     * @param cluster1
     * @param cluster2
     * @return
     */
    public static double find(ArrayList<Cell> cluster1, ArrayList<Cell> cluster2, int dataSize) {
        //find the centroids of the clusters
        Data centroid1 = CentroidFinder.find(cluster1, dataSize);
        Data centroid2 = CentroidFinder.find(cluster2, dataSize);
        return new EuclideanDistanceDataComparator().getDistanceBetween(centroid1, centroid2);
    }
}
