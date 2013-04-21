/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package a2;

import a2.algorithm.ACO;

/**
 *
 * @author phlippie
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ACO aco = new ACO (15, 60, 5, 20, 5, 10);
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

        
    }

}
