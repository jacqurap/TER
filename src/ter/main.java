/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import ilog.concert.IloException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author jacqu
 */
public class main {
    
    final static String filename = "src/inputs/tmp";
    
    public static void main(String[] args) throws FileNotFoundException, IloException{
        Generateur generateur = new Generateur(80);
        Plan p = generateur.newInstance();
        System.out.println(p.toString());
        p.writeInFile(filename);
        
        /*
        Plan p = new Plan(4);
        p.L = 10;
        for(int i = 0; i<4; i++){
            p.U[i] = 20 + i;
            p.d[i] =7+i;
        }
        PLNE.model(p);
        
        ArrayList<Integer> X = new ArrayList<>();
        ArrayList<Integer> U = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();
        for(int i = 0; i<4; i++){
            X.add(0);
            U.add(p.U[i]);
            d.add(p.d[i]);
        }
*/
        long startTime, stopTime;
        startTime = System.currentTimeMillis();
        long plne = PLNE.model(p);
        stopTime = System.currentTimeMillis();
        long PLNETime = stopTime - startTime;
        
        System.out.println("---------------------");
        
        startTime = System.currentTimeMillis();
        int ret = Solver.slove(p);
        stopTime = System.currentTimeMillis();
        long SolverTime = stopTime - startTime;
        
        System.out.println("plne =  \t" + plne + " \ttime = " + PLNETime);
        System.out.println("solver =\t" + ret + " \ttime = " + SolverTime);
        //int[] X = new int[p.T];
        //int ret = Solver.rec(p.U, p.d, X, 10, 2);
        //System.out.println("X = " + Arrays.toString(X));
        
        /*
        int[] U = {23, 21, 25, 20, 21, 28, 26};
        int[] d = {7, 2, 2, 20, 1, 2, 15};
        int[] X = {0,0,0,0,0,0,0};
        ret = Solver.rec(U, d, X, 10, 5);
        System.out.println("ret = " + ret);
        //Solver.findL(U, d, X, 10, 5, 16);
        System.out.println(Arrays.toString(U));
        System.out.println(Arrays.toString(d));
        System.out.println(Arrays.toString(X));*/
//        optimum.findOptimum(filename);
//        System.out.println("X = " + X.toString());

        
    }
}

















