/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import java.io.File;
import java.util.Random;

/**
 *
 * @author jacqu
 */
public class Generateur {
    
    private int T;
    Random rand;
    private int d[] = {1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9,15,20,25,35};
    
    public Generateur(){
        this(40);
    }
    public Generateur(int t){
        rand = new Random();
        int seed = rand.nextInt();
        System.out.println("seed = " + seed);
        rand = new Random(seed);
        T = t;
    }
    
    public Plan newInstance(){
        Plan plan = new Plan(this.T);
        plan.L = 10;
        int r;
        for(int i = 0; i<T; i++){
            plan.d[i] = d[rand.nextInt(22)];
        }
        for(int i = 0; i<T; i++){
            plan.U[i] = rand.nextInt(11) + 20;
        }
        return plan;
    }
    
}
