/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ter;

import java.util.Arrays;

/**
 *
 * @author jacqu
 */
public class Solver {

    public static int findL(int[] U, int[] D, int[] X, int L, int v, int eps) {
        int DtoV = 0;
        int nextL = eps;
        int c = 0;

        for (int i = v; i >= 0; i--) {
            DtoV += D[i];
            if (DtoV >= nextL) {
                DtoV -= nextL;
                X[i] = nextL;
                nextL = L;
                while ((DtoV >= nextL) && (X[i] + nextL <= U[i])) {
                    DtoV -= nextL;
                    X[i] += nextL;
                    nextL = L;
                }
            } else {
                X[i] = 0;
            }
            c += DtoV;
        }
        if (DtoV > 0) {
            return Integer.MAX_VALUE / 2;
        }
        return c;
    }

    public static int rec(int[] U, int[] D, int[] X, int L, int v) {

        int dv = D[v];
        int Dtot = 0;

        if (v == 0) {
            if (dv == 0) {
                X[0] = 0;
                return 0;
            } else if (dv < L) {
                return Integer.MAX_VALUE / 2;
            } else if (dv <= U[0]) {
                X[0] = dv;
                return 0;
            } else {
                return Integer.MAX_VALUE / 2;
            }
        }
        for (int i = 0; i <= v; i++) {
            Dtot += D[i];
        }
        if (dv <= L) {
            X[v] = 0;
            D[v - 1] += dv;
            int c = rec(U, D, X, L, v - 1) + dv;
            D[v - 1] -= dv;
            return c;
        } else if (dv <= U[v]) {
            return findL(U, D, X, L, v, L + (Dtot % L)); // car si Xv = 0, alors aucune prod precedente ne peut etre superieur à 2L
        } else { //dv > Uv
            // soit Xv < Uv
            int eps = (Dtot % L) + L;
            int c1 = findL(U, D, X, L, v, eps);
            // soit Xv = Uv
            D[v - 1] += dv - U[v];
            X[v] = U[v];
            int c2 = rec(U, D, X, L, v - 1) + dv - U[v];
            D[v - 1] += U[v] - dv;
            if (c1 <= c2) {
                return findL(U, D, X, L, v, L + (Dtot % L)); //je le refais pour que les X de sortie soient les bons
            } else {
                return c2;
            }
        }
    }

    public static int slove(Plan p) {
        int[] X = new int[p.T];

        int[] cost = new int[p.T + 1];
        for (int i = 0; i < p.T + 1; i++) {
            cost[i] = Integer.MAX_VALUE;
        }
        cost[0] = 0;
        int[] path = new int[p.T + 1];
        for (int i = 0; i < p.T + 1; i++) {
            path[i] = -1;
        }
        int[] tmpD = new int[p.T];
        int[] tmpU = new int[p.T];
        int[] tmpX = new int[p.T];

        int c = 0;
        for (int u = 0; u < p.T; u++) {
            if (cost[u] < Integer.MAX_VALUE / 2) {
                for (int v = u; v < p.T; v++) {
                    System.arraycopy(p.d, u, tmpD, 0, v - u + 1);
                    System.arraycopy(p.U, u, tmpU, 0, v - u + 1);
                    System.arraycopy(X, u, tmpX, 0, v - u + 1);
                    c = rec(tmpU, tmpD, tmpX, p.L, v - u);
                    if (c < Integer.MAX_VALUE / 2) {
                        if (cost[v + 1] > (cost[u] + c)) {
                            cost[v + 1] = cost[u] + c;
                            path[v + 1] = u;
                        }
                    }
                }
            }
        }
        System.out.println("cout final : " + cost[p.T]);
        System.out.println("cost : " + Arrays.toString(cost));
        System.out.println("path : " + Arrays.toString(path));

        if (cost[p.T] < Integer.MAX_VALUE / 2) {
            //reconstruction de la solution
            int v = p.T;
            int u;
            while (v > 0) {
                u = path[v];
                System.arraycopy(p.d, u, tmpD, 0, v - u);
                System.arraycopy(p.U, u, tmpU, 0, v - u);
                System.arraycopy(X, u, tmpX, 0, v - u);
                rec(tmpU, tmpD, tmpX, p.L, v - u - 1);
                System.arraycopy(tmpX, 0, X, u, v - u);
                v = u;
            }
            System.out.println("X : " + Arrays.toString(X));
        }
        return (cost[p.T]);
    }
}
