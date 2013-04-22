/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package a2;

import a2.Evaluation.ClusterEvaluator;
import a2.Evaluation.ClusterFinder;
import a2.algorithm.ACO;
import a2.algorithm.ACOWithDataMemory;
import a2.algorithm.ACOWithDensityMemory;
import a2.grid.Cell;
import a2.grid.Grid;
import java.util.ArrayList;

/**
 *
 * @author phlippie
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // test args:
        if (args.length != 13) {
            System.out.println("Usage:");
            System.out.println("Main <iterations> <x size> <y size> <number of ants> <number of data vectors> <data vector size> <data vector range> <gamma> <gamma1> <gamma2> <ant velocity> <ant memory size> <ant memory bias U(0,1)>");
            return;
        }

        double iterations = -1;
        int xSize = -1;
        int ySize = -1;
        int numberOfAnts = -1;
        int numberOfDataVectors = -1;
        int dataVectorSize = -1;
        int dataVectorRange = -1;
        double gamma = -1;
        double gamma1 = -1;
        double gamma2 = -1;
        double antVelocity = -1;
        int antMemorySize = -1;
        double antMemoryBias = -1;

        try {
            iterations = Double.parseDouble(args[0]);
            xSize = Integer.parseInt(args[1]);
            ySize = Integer.parseInt(args[2]);
            numberOfAnts = Integer.parseInt(args[3]);
            numberOfDataVectors = Integer.parseInt(args[4]);
            dataVectorSize = Integer.parseInt(args[5]);
            dataVectorRange = Integer.parseInt(args[6]);
            gamma = Double.parseDouble(args[7]);
            gamma1 = Double.parseDouble(args[8]);
            gamma2 = Double.parseDouble(args[9]);
            antVelocity = Double.parseDouble(args[10]);
            antMemorySize = Integer.parseInt(args[11]);
            antMemoryBias = Double.parseDouble(args[12]);


            System.out.println ("Using a " + xSize + " by " + ySize + " grid with " + numberOfAnts + " ants and " + numberOfDataVectors + " data vectors of size " + dataVectorSize + " and range " + dataVectorRange + ".");
            System.out.println ("gamma = "+gamma+"; gamma1 = "+gamma1+"; gamma2 = "+gamma2);
            System.out.println ("ant speed: " + antVelocity);
            System.out.println ("ants have memory of "+antMemorySize+" items and are biased with P="+antMemoryBias);
        } catch (Exception e) {
            System.out.println ("Invalid argument passed.");
            System.out.println (e);
            return;
        }

        if (iterations == -1 ||
            xSize == -1 ||
            ySize == -1 ||
            numberOfAnts == -1 ||
            numberOfDataVectors == -1 ||
            dataVectorSize == -1 ||
            dataVectorRange == -1 ||
            gamma == -1 ||
            gamma1 == -1 ||
            gamma2 == -1 ||
            antMemoryBias == -1 ||
            antMemorySize == -1) {
            System.out.println ("Invalid or unitialized args.");
            return;
        }



        //ACOWithDataMemory aco = new ACOWithDataMemory (xSize, ySize, numberOfAnts, numberOfDataVectors, dataVectorSize, dataVectorRange, antMemorySize);
        ACOWithDensityMemory aco = new ACOWithDensityMemory (xSize, ySize, numberOfAnts, numberOfDataVectors, dataVectorSize, dataVectorRange, antMemorySize);
        aco.setGamma(gamma);
        aco.setGamma1(gamma1);
        aco.setGamma2(gamma2);
        aco.setAntVelocity((int)antVelocity);
        aco.setMemoryBias(antMemoryBias);
        //System.out.println ("\nBEFORE:\n");
        //aco.print();
        
        int percent = 0;
        for (double i = 0; i < iterations; i++) {
            aco.iterate();
        }
        aco.stop();
//        System.out.println("\n\nAFTER:\n");
//        aco.print();
//        System.out.println();

        ArrayList <ArrayList <Cell> > clusters = ClusterFinder.find(aco.getGrid());
/*        System.out.println ("found clusters: " + clusters.size());
        for (int i = 0; i < clusters.size(); i++) {
            Grid g = new Grid (xSize,ySize);
            for (int j = 0; j < clusters.get(i).size(); j++) {
                g.setCellData(clusters.get(i).get(j).getX(),
                        clusters.get(i).get(j).getY(),
                        clusters.get(i).get(j).getData());
            }
            System.out.println("\nCluster "+ i + ":");
            g.print();
            System.out.println();
        }*/

        ClusterEvaluator.evaluate(clusters, dataVectorSize, numberOfDataVectors);

        
    }

}
