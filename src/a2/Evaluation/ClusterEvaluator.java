package a2.Evaluation;

import a2.grid.Cell;
import java.util.ArrayList;

/**
 * Evaluates a collection of clusters
 * in terms of inter- and intra-cluster distances.
 * @author phlippie
 */
public class ClusterEvaluator {

    public static void evaluate (ArrayList <ArrayList <Cell> > clusters, int dataSize, int totalDataCount) {

        // find the total number of clustered data
        int totalClusteredData = 0;
        for (ArrayList <Cell> cluster : clusters) {
            for (Cell cell : cluster) {
                totalClusteredData ++;
            }
        }

        int totalUnclusteredData = totalDataCount - totalClusteredData;
        double clusteredData = totalClusteredData / (double)totalDataCount;

        // find all the intra-cluster distances
        ArrayList <Double> intraClusterDistances = new ArrayList <Double> ();
        for (ArrayList <Cell> cluster : clusters) {
            double intraClusterDistance = IntraClusterDistance.find(cluster, dataSize);
            intraClusterDistances.add(intraClusterDistance);
        }

        // find all the inter-cluster distances
        ArrayList <Double> interClusterDistances = new ArrayList <Double> ();
        for (ArrayList <Cell> cluster1 : clusters) {
            for (ArrayList <Cell> cluster2 : clusters) {
                if (cluster1 != cluster2) {
                    double interClusterDistance = InterClusterDistance.find(cluster1, cluster2, dataSize);
                    interClusterDistances.add(interClusterDistance);
                }
            }
        }

        // find the averages
        double averageIntraClusterDistance = 0.0;
        for (Double intraClusterDistance : intraClusterDistances) {
            averageIntraClusterDistance += intraClusterDistance;
        }
        averageIntraClusterDistance /= (double)clusters.size();

        double averageInterClusterDistance = 0.0;
        for (Double interClusterDistance : interClusterDistances) {
            averageInterClusterDistance += interClusterDistance;
        }
        averageInterClusterDistance /= (double)connectionCount(clusters.size());

        // find average cluster size
        double averageClusterSize = 0.0;
        for (ArrayList <Cell> cluster : clusters) {
            averageClusterSize += cluster.size();
        }
        averageClusterSize /= clusters.size();

        System.out.println();
        System.out.println ("Number of clusters:   " + clusters.size());
        System.out.println ("Average cluster size: " + averageClusterSize);
        System.out.println ("Clustered data :" + totalClusteredData + "/" + totalDataCount + " (" + (clusteredData*100) + "%)");
        System.out.println ("Average intra-cluster distance: " +averageIntraClusterDistance);
        System.out.println ("Average inter-cluster distance: " +averageInterClusterDistance);

    }

    static int connectionCount (int nodes) {
        int difference = 1;
        int result = 0;
        for (int i = nodes - 1; i > 0; i--, difference++) {
            result += difference;
        }
        return result;
    }
}
