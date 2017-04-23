/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author jacqu
 */
public class Plan {
    
    public int T;
    public int L;
    public int[] d;
    public int[] U;
    
    public Plan(int T, int L, int[] d, int[] U){
        this.T = T;
        this.L = L;
        this.U = new int[T];
        System.arraycopy(U, 0, this.U, 0, T);
        this.d = new int[T];
        System.arraycopy(d, 0, this.d, 0, T);
    }
    public Plan(int T){
        this.T = T;
        U = new int[T];
        d = new int[T];
        for(int i = 0; i<T; i++){
            U[i] = 0;
            d[i] = 0;
        }
    }
    
    public void setU(int t, int Ut){
        U[t] = Ut;
    }
    public void setD(int t, int dt){
        d[t] = dt;
    }
    
    public void writeInFile(String filename) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(filename);
        writer.println(L);
        writer.println(UtoStr());
        writer.println(DtoStr());
        writer.close();
    }
    
    public String toString(){
        return L + "\n" + UtoStr() + "\n" + DtoStr();
    }
    
    public String UtoStr(){
        String strU = "" + U[0];
        for(int i = 1; i<T; i++){
            strU = strU + " " + U[i];
        }
        return strU;
    }
    public String DtoStr(){
        String strD = "" + d[0];
        for(int i = 1; i<T; i++){
            strD = strD +" " + d[i];
        }
        return strD;
    }
}
