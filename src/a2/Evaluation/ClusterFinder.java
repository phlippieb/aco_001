package a2.Evaluation;

import a2.grid.Cell;
import a2.grid.Grid;
import java.util.ArrayList;

/**
 * A tool to find clusters on a grid.
 * @author phlippie
 */
public class ClusterFinder {

    /**
     * finds the clusters in a grid. A cluster is defined as a group of at least two contiguous dataVectors.
     * Diagonal vectors are considered contiguous because patches include diagonal sites.
     * @param grid The grid to search for clusters
     * @return A list of lists of Cells containing DataVectors; each sublist is a cluster.
     */
    public static ArrayList<ArrayList<Cell>> find (Grid grid) {

        //set up a list for checking whether coordinates have been checked yet
        boolean [][] availableCoordinates = new boolean [grid.getXSize()][grid.getYSize()];
        for (int i = 0; i < availableCoordinates.length; i++) {
            for (int j = 0; j < availableCoordinates[i].length; j++) {
                availableCoordinates[i][j] = true;
            }
        }

        ArrayList<ArrayList<Cell>> results = new ArrayList<ArrayList<Cell>>();

        for (int i = 0; i < availableCoordinates.length; i++) {
            for (int j = 0; j < availableCoordinates[i].length; j++) {
                if (availableCoordinates[i][j]) {
                    ArrayList <Cell> cluster = findNeighbours(i, j, grid, availableCoordinates);
                    if (!cluster.isEmpty() && cluster.size() > 1) {
                        results.add(cluster);
                        availableCoordinates[i][j] = false;
                    }
                }
            }
        }

        return results;
    }

    static ArrayList<Cell> findNeighbours (int x, int y, Grid grid, boolean [][] availableCoordinates) {
        
        ArrayList<Cell> results = new ArrayList<Cell>();
        
        //check if cell is still unchecked
        if (availableCoordinates[x][y]) {
            //remove self from unchecked coordinates
            availableCoordinates[x][y] = false;

            //try self
            if (grid.cellHasData(x, y)) {
                results.add(grid.getCell(x, y));

                //try each of 8 neighbouring cells
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <=1; j++) {
                        if (grid.isValidPosition(x+i, y+j) &&
                            availableCoordinates[x+i][y+j]) {
                            results.addAll(findNeighbours(x+i,y+j,grid,availableCoordinates));
                        }
                    }
                }
            }

            
        } else {
            availableCoordinates[x][y] = false;
        }

        return results;
    }
}
