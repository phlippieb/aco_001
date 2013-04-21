/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package a2;

import a2.algorithm.ACO;
import javax.print.attribute.standard.NumberOfDocuments;

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
        if (args.length != 10) {
            System.out.println("Usage:");
            System.out.println("Main <x size> <y size> <number of ants> <number of data vectors> <data vector size> <data vector range> <gamma> <gamma1> <gamma2> <ant velocity>");
            return;
        }

            int xSize = -1;
            int ySize = -1;
            int numberOfAnts = -1;
            int numberOfDataVectors = -1;
            int dataVectorSize = -1;
            int dataVectorRange = -1;
            double gamma = -1;
            double gamma1 = -1;
            double gamma2 = -1;
            int antVelocity = -1;


        try {
            xSize = Integer.parseInt(args[0]);
            ySize = Integer.parseInt(args[1]);
            numberOfAnts = Integer.parseInt(args[2]);
            numberOfDataVectors = Integer.parseInt(args[3]);
            dataVectorSize = Integer.parseInt(args[4]);
            dataVectorRange = Integer.parseInt(args[5]);
            gamma = Double.parseDouble(args[6]);
            gamma1 = Double.parseDouble(args[7]);
            gamma2 = Double.parseDouble(args[8]);
            antVelocity = Integer.parseInt(args[9]);

            System.out.println ("Using a " + xSize + " by " + ySize + " grid with " + numberOfAnts + " ants and " + numberOfDataVectors + " data vectors of size " + dataVectorSize + " and range " + dataVectorRange + ".");
            System.out.println("gamma = "+gamma+"; gamma1 = "+gamma1+"; gamma2 = "+gamma2);
            System.out.println ("maximum ant velocity is " + antVelocity);
        } catch (Exception e) {
            System.out.println ("Invalid argument passed.");
            System.out.println (e);
            return;
        }

        if (xSize == -1 ||
            ySize == -1 ||
            numberOfAnts == -1 ||
            numberOfDataVectors == -1 ||
            dataVectorSize == -1 ||
            dataVectorRange == -1 ||
            gamma == -1 ||
            gamma1 == -1 ||
            gamma2 == -1 ||
            antVelocity == -1) {
            System.out.println ("Invalid or unitialized args.");
            return;
        }



        ACO aco = new ACO (xSize, ySize, numberOfAnts, numberOfDataVectors, dataVectorSize, dataVectorRange);
        aco.setGamma(gamma);
        aco.setGamma1(gamma1);
        aco.setGamma2(gamma2);
        aco.setAntVelocity(antVelocity);
        System.out.println ("\nBEFORE:\n");
        aco.print();
        double iterations = 10000;

        int percent = 0;
        for (double i = 0; i < iterations; i++) {
            aco.iterate();
            /*if (i % 100 == 0) {
                if ((int)(i / iterations * 100) % 10 == 0 && percent != (int)(i / iterations * 100)) {
                    percent = (int)(i / iterations * 100);
                    System.out.println(percent + "%");
                }
            }*/
        }
        aco.stop();
        System.out.println("\n\nAFTER:\n");
        aco.print();
        System.out.println();

        
    }

}
