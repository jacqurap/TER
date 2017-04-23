/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import ilog.concert.*;
import ilog.cplex.*;
/**
 *
 * @author jacqu
 */
public class PLNE {
    
    public static long model(Plan p) throws IloException{
        
        IloCplex cplex = new IloCplex();
        
        
        //variables
        IloNumVar[] I = new IloNumVar[p.T];
        IloNumVar[] X = new IloNumVar[p.T];
        IloNumVar[] bool = new IloNumVar[p.T];
        
        I = cplex.numVarArray(p.T, 0, Double.MAX_VALUE);
        X = cplex.numVarArray(p.T, 0, Double.MAX_VALUE);
        bool = cplex.boolVarArray(p.T);
        
        //objective
        IloLinearNumExpr obj = cplex.linearNumExpr();
        for(int i = 0; i < p.T; i++){
            obj.addTerm(1, I[i]);
        }
        cplex.addMinimize(obj);
        
        
        //constraints
        for(int i = 0; i<p.T; i++){
            // flow constraint
            IloLinearNumExpr exprFlow = cplex.linearNumExpr();
            exprFlow.addTerm(1, X[i]);
            if(i>0){
                exprFlow.addTerm(1, I[i-1]);
            }
            exprFlow.addTerm(I[i], -1);
            cplex.addEq(exprFlow, p.d[i]);
            
            //UL constraints
            IloLinearNumExpr exprL = cplex.linearNumExpr();
            IloLinearNumExpr exprU = cplex.linearNumExpr();
            exprL.addTerm(p.L, bool[i]);
            exprU.addTerm(p.U[i], bool[i]);
            cplex.addLe(exprL, X[i]);
            cplex.addLe(X[i], exprU);
        }
        
        
        // stock final nul
        cplex.addEq(I[p.T-1], 0); // a suprimmer si l'on accepte un stock en final
        
        cplex.setParam(IloCplex.Param.MIP.Display, 0);
        
        
        //solve
        if(cplex.solve()){
            long resultat = Math.round(cplex.getObjValue());
            System.out.println("objectif = " + resultat);
            System.out.print("X = [");
            for(int i = 0; i<p.T; i++){
                System.out.print(Math.round(cplex.getValue(X[i]) )+ ", ");
            }
            System.out.println("]");
            System.out.print("I = [");
            for(int i = 0; i<p.T; i++){
                System.out.print(Math.round(cplex.getValue(I[i])) + ", ");
            }
            
            System.out.println("]");
            return resultat;
        }
        
        
        cplex.end();
        return -1;
    }
}