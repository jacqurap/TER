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
        
        System.out.println("---------------------");
        System.out.println("plne =  \t" + plne + " \ttime = " + PLNETime);
        System.out.println("solver =\t" + ret + " \ttime = " + SolverTime);
    }
}

















