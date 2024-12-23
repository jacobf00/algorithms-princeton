package org.example;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    
    private int trials;
    private double[] fractionOpenToPercolate;
    public long endTime;

     // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        long startTime = System.currentTimeMillis();
        this.trials = trials;
        this.fractionOpenToPercolate = new double[trials];

        for(int i=0; i < trials; i++){
            Percolation percolationClient = new Percolation(n);
            boolean percolates = false;
            while(!percolates){
                percolationClient.open(StdRandom.uniformInt(1, n+1), StdRandom.uniformInt(1, n+1));
                percolates = percolationClient.percolates();
                if(percolates){
                    this.fractionOpenToPercolate[i] = percolationClient.numberOfOpenSites()/(double)(n*n);
                }
            }
        }
        this.endTime = System.currentTimeMillis() - startTime;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.fractionOpenToPercolate);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionOpenToPercolate);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return(this.mean() - (1.96 * this.stddev())/(Math.sqrt(this.trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return(this.mean() + (1.96 * this.stddev())/(Math.sqrt(this.trials)));
    }

   // test client (see below)
   public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("Need two input arguments n, and T");
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, T);

        System.out.printf("Percolation simulation took: %d ms\n", percolationStats.endTime);
        // System.out.println(Arrays.toString(this.fractionOpenToPercolate));
        System.out.println("mean = " + percolationStats.mean()); 
        System.out.println("stddev = " + percolationStats.stddev());
        System.out.println("[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
   }
}
