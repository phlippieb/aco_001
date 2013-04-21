/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package a2.algorithm;

import a2.ant.PositionMemoryAnt;
import a2.ant.RememberedPosition;
import a2.data.Data;
import a2.data.DataComparator;
import a2.data.EuclideanDistanceDataComparator;
import a2.grid.Cell;
import a2.grid.Grid;
import java.util.ArrayList;

/**
 * The main branch version of this algorithm has no velocity or memory.
 * For ant velocity or short term memory, check out the other branches.
 * @author phlippie
 */

public class ACOWithDensityMemory {

    Grid grid;
    PositionMemoryAnt [] ants;
    Data [] data;
    int dataSize = 5;
    int dataRange = 10;
    DataComparator dataComparator = new EuclideanDistanceDataComparator ();
    int patchRadius = 1; // The patch is a square with sides of 2r + 1
    double gamma = 0.01;
    double gamma1 = 2.5;
    double gamma2 = 2.5;
    int maxAntVelocity;
    double memoryBias = 0.5; // probability of going with best remembered position

    /**The patch will be a square around an ant, with sides of length 2r+1.
     * @param r
     */
    public void setPatchRadius (int r) {
        this.patchRadius = r;
    }

    /**The gamma constant scales the dissimilarity between data vectors.
     * @param g
     */
    public void setGamma (double g) {
        this.gamma = g;
    }

    /**The gamma1 constant scales the probability that an ant will pick up an item.
     * @param g1
     */
    public void setGamma1 (double g1) {
        this.gamma1 = g1;
    }

    /**The gamma2 constant scales the probability that an ant will drop an item.
     * @param g2
     */
    public void setGamma2 (double g2) {
        this.gamma2 = g2;
    }

    public void setAntVelocity (int v) {
        this.maxAntVelocity = v;
    }

    public void setMemoryBias (double bias) {
        if (0 <= bias && bias <= 1) {
            this.memoryBias = bias;
        } else {
            throw new RuntimeException ("Invalid memory bias; must be in U(0,1)");
        }
    }

    public ACOWithDensityMemory (int xSize, int ySize, int numberOfAnts, int numberOfData, int dataSize, int dataRange, int antMemorySize) {

        //init grid and ant array
        this.grid = new Grid (xSize, ySize);
        this.ants = new PositionMemoryAnt [numberOfAnts];
        this.data = new Data [numberOfData];

        //init data size and range
        this.dataSize = dataSize;
        this.dataRange = dataRange;

        //housekeeping
        int randomX;
        int randomY;

        //init data vectors randomly and place randomly on grid
        for (int i = 0; i < numberOfData; i++) {
            do {
                randomX = (int)(Math.random() * xSize);
                randomY = (int)(Math.random() * ySize);
            } while (grid.cellHasData(randomX, randomY));

            //initialize the datavector with random data
            ArrayList randomData = new ArrayList();
            for (int j = 0; j < dataSize; j++) {
                randomData.add(new Double((int)(Math.random()) * dataRange));
                //randomData.add(new Double(5.0)); //test with one type to force clusters
            }

            Data newData = new Data (randomData);
            grid.setCellData(randomX, randomY, newData);
            this.data[i] = newData;
        }

        //init ants and place randomly on grid
        for (int i = 0; i < numberOfAnts; i++) {
            do {
                randomX = (int)(Math.random() * xSize);
                randomY = (int)(Math.random() * ySize);
            } while (grid.cellHasAnt(randomX, randomY));

            PositionMemoryAnt newAnt = new PositionMemoryAnt (randomX, randomY, antMemorySize);
            grid.setCellAnt(randomX, randomY, newAnt);
            ants[i] = newAnt;
        }
    }

    public void print () {
        this.grid.print();
    }

    public void iterate () {
        //
        // for all ants
        //  if ant is unladen and cell has data
        //      compute density
        //      compute pickup probability
        //      possibly pick up data
        // else if ant is laden and cell is empty
        //      compute density
        //      compute drop probability
        //      possibly drop data
        //  move to random nearby cell not occupied by another ant within speed
        //

        double neighbourhoodDensity = 0.0, probability = 0.0;
        int antX, antY;

        for (PositionMemoryAnt ant : ants) {
            antX = ant.getX();
            antY = ant.getY();
            if (!ant.hasData() && grid.cellHasData(antX, antY)) {
                neighbourhoodDensity = computeDensity(grid.getCellData(antX, antY), grid, antX, antY);
                probability = computePickupProbability(neighbourhoodDensity);
                if (randomBoolean(probability)) {
                    ant.setData(grid.clearCellData(antX, antY));
                }
            } else if (ant.hasData() && !grid.cellHasData(antX, antY)) {
                neighbourhoodDensity = computeDensity(ant.getData(), grid, antX, antY);
                probability = computeDropProbability(neighbourhoodDensity);
                if (randomBoolean(probability)) {
                    //remember position and put down (need to calculate density wrt specific data later)
                    ant.remember(antX, antY);
                    grid.setCellData(antX, antY, ant.clearData());

                }

            }

            //bias ant's movement towards best remembered position if ant has data
            //if ant chooses not follow bias, clear memory and move normally
            if (ant.hasData() && ant.hasMemory()) {
                if (randomBoolean (this.memoryBias)) {
                    // calculate best fit:
                    RememberedPosition bestPosition = calculateBestPosition (ant.getData(), ant.getMemory(), this.grid);
                    // move towards best fit:
                    moveAntTowards(ant, bestPosition.x, bestPosition.y);
                } else {
                    ant.clearMemory();
                    moveAnt (ant, grid);
                }
            } else {
                moveAnt (ant, grid);
            }
        }
    }

    //Put down data
    public void stop () {
        int x, y;
        for (int i = 0; i < ants.length; i++) {
            x = ants[i].getX();
            y = ants[i].getY();
            grid.setCellData(x, y, ants[i].getData());
        }
    }

    protected double computeDensity (Data d, Grid grid, int x, int y) {
        Data theData = d.clone();
        if (theData == null) {
            return 0.0;
        }
        ArrayList<Data> patchData = getPatchData (grid, x, y, this.patchRadius);
        double sum = 0.0;
        double distance = 0.0;
        double velocity = 0.0;
        for (int i = 0; i < patchData.size(); i++) {
            distance = dataComparator.getDistanceBetween(theData, patchData.get(i));
            velocity = (Math.random() * (double)(maxAntVelocity -1)) + 1.0;
            sum += (1 - (distance / (gamma * (1 - ((velocity -1)/(maxAntVelocity))))));
        }
        if (sum < 0.0) {
            return 0.0;
        } else {
            return sum / (double)(2*patchRadius+1);
        }
    }

    protected ArrayList<Data> getPatchData (Grid grid, int x, int y, int patchSize) {
        ArrayList<Data> result = new ArrayList<Data>();
        for (int i = 0 - patchSize; i < patchSize; i++) {
            for (int j = 0 - patchSize; i < patchSize; i++) {
                if (grid.isValidPosition(x + i, y + j)) {
                    if (grid.cellHasData(x + i, y + j)) {
                        if (!(i == 0 && j == 0)) {
                            result.add(grid.getCellData(x + i, y + j));
                        }
                    }
                }
            }
        }
        return result;
    }

    protected double computePickupProbability (double density) {
        return Math.pow ((gamma1 / (gamma1 + density)), 2);
    }

    protected double computeDropProbability (double density) {
        if (density >= gamma2) {
            return 1.0;
        } else {
            return 2.0 * density;
        }
    }

    protected void moveAnt (PositionMemoryAnt ant, Grid grid) {
        // Ant should move to a randomly selected neighbouring site
        //  not occupied by another ant.
        // If no site is available, ant stays put.

        // make list of available cells.
        // do not add own site.
        ArrayList<Cell> availableSites = new ArrayList<Cell>();
        int x = ant.getX();
        int y = ant.getY();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (grid.isValidPosition(x + i, y + j) &&
                    ! grid.cellHasAnt(x + i, y + j) &&
                    ! (i == 0 && j == 0)) {
                    availableSites.add(grid.getCell(x+i,y+j));
                }
            }
        }

        // if no sites are available, stay put.
        if (availableSites.size() == 0) {
            return;
        }

        // select a random cell from available sites and move ant there.
        else {
            // clear current cell of ant
            grid.clearCellAnt(x, y);

            // select random available cell
            //Cell randomCell = availableSites.get((int)(Math.round(Math.random() * availableSites.size())));
            Cell randomCell = availableSites.get((int)(Math.random() * availableSites.size()));

            // set ant position to new cell
            ant.setX(randomCell.getX());
            ant.setY(randomCell.getY());

            // set cell to contain new ant
            grid.setCellAnt(randomCell.getX(), randomCell.getY(), ant);
        }
    }

    void moveAntTowards (PositionMemoryAnt ant, int x, int y) {
        //move ant in direction biased towards (x,y), eg:
        // a > x
        //or
        // x  <
        //      a
        int antx = ant.getX();
        int anty = ant.getY();
        ant.setX(antx == x ? antx :
                    antx < x ? antx +1 :
                    antx > x ? antx -1 : antx);
        ant.setY(anty == y ? anty :
                    anty < y ? anty +1 :
                    anty > y ? anty -1 : anty);
    }

    /**
     * Randomly returns true or false.
     * @param probability The probability of returning true.
     * @return
     */
    protected boolean randomBoolean (double probability) {
        if (Math.random() <= probability) {
            return probability != 0.0; //would still return true if random and probability were 0.
        }
        return false;
    }

    private RememberedPosition calculateBestPosition(Data data, RememberedPosition[] memories, Grid grid) {
        RememberedPosition bestPosition = null;
        double bestDensity = 0.0;
        for (RememberedPosition memory : memories) {
            if (bestPosition == null) {
                bestPosition = memory.clone();
                bestDensity = computeDensity (data, grid, memory.x, memory.y);
            } else {
                if (bestDensity < computeDensity (data, grid, memory.x, memory.y)) {
                    bestPosition = memory.clone();
                    bestDensity = computeDensity (data, grid, memory.x, memory.y);
                }
            }
        }
        return bestPosition;
    }
}
