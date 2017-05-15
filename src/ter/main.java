/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import ilog.concert.IloException;
import java.io.FileNotFoundException;

/**
 *
 * @author jacqu
 */
public class main {

    //final static String filename = "src/inputs/tmp";
    public final static boolean AFFICHAGE = false;
    public final static int NB_MESURES = 10;

    public static void main(String[] args) throws FileNotFoundException, IloException, DifferentResultException {
        tests();
    }

    public static void testUnitaire() throws FileNotFoundException, IloException {
        Generateur generateur = new Generateur(200);
        Plan p = generateur.newInstance();

        long startTime, stopTime;
        startTime = System.currentTimeMillis();
        long plne = PLNE.model(p, false);
        stopTime = System.currentTimeMillis();
        long PLNETime = stopTime - startTime;

        System.out.println("---------------------");

        startTime = System.currentTimeMillis();
        long ret = Solver.slove(p, true);
        stopTime = System.currentTimeMillis();
        long SolverTime = stopTime - startTime;

        System.out.println("---------------------");
        System.out.println("plne =  \t" + plne + " \ttime = " + PLNETime);
        System.out.println("solver =\t" + ret + " \ttime = " + SolverTime);
    }

    public static void tests() throws FileNotFoundException, IloException, DifferentResultException {
        int[] tests = {20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300};
        long cPLNE, cSolver, startTime, stopTime, PLNETime, solverTime, totPLNETime, totSolverTime;
        System.out.println("t \t plne \t solver");

        Generateur generateur = new Generateur();
        for (int t : tests) {
            generateur.setT(t);
            totSolverTime = 0;
            totPLNETime = 0;
            for (int i = 0; i < NB_MESURES; i++) {
                Plan p = generateur.newInstance();

                startTime = System.currentTimeMillis();
                cPLNE = PLNE.model(p, false);
                stopTime = System.currentTimeMillis();
                PLNETime = stopTime - startTime;

                startTime = System.currentTimeMillis();
                cSolver = Solver.slove(p, true);
                stopTime = System.currentTimeMillis();
                solverTime = stopTime - startTime;

                if (cPLNE != cSolver) {
                    throw new DifferentResultException(generateur.getSeed(), p);
                }
                totPLNETime += PLNETime;
                totSolverTime += solverTime;
            }
            System.out.println(t + " \t " + (totPLNETime/NB_MESURES) + " \t " + (totSolverTime/NB_MESURES));
        }
    }
}

class DifferentResultException extends Exception {

    public DifferentResultException(int seed, Plan p) {
        System.out.println("les retours sont differents");
        System.out.println("Seed : " + seed);
        System.out.println("Plan : ");
        System.out.println(p.toString());
    }
}
