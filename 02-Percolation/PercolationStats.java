import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;
import java.lang.Integer;


public class PercolationStats {

   //private double threshold;
   private final double[] results;

   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {
     results = new double[trials];

     for (int i = 0;i < trials ;++i ) {

         Percolation per = new Percolation(n);
         while(!per.percolates()) {
           //random uniformly in [1,n^2]
           int rand_x = StdRandom.uniform(1, n+1);
           int rand_y = StdRandom.uniform(1, n+1);

           //System.out.println("pos_x: " + rand_x  + ", pos_y: " + rand_y);
           per.open(rand_x, rand_y);
        }
        int numberOpenSites = per.numberOfOpenSites();
        results[i] = (double)numberOpenSites/(n*n);
        //System.out.println("numberOpenSites: " + numberOpenSites);
    }
   }

   // sample mean of percolation threshold
   public double mean() {
     return StdStats.mean(results);
   }

   // sample standard deviation of percolation threshold
   public double stddev() {
     return StdStats.stddev(results);
   }

   // low  endpoint of 95% confidence interval
   public double confidenceLo() {
     return mean() - (1.96*stddev()/Math.sqrt(results.length));
   }

   // high endpoint of 95% confidence interval
   public double confidenceHi() {
     return mean() + (1.96*stddev()/Math.sqrt(results.length));
   }

// test client (described below)
   public static void main(String[] args) {
     int n = Integer.parseInt(args[0]);
     int trials = Integer.parseInt(args[1]);

     PercolationStats  ps = new PercolationStats(n, trials);

     System.out.println("mean: " + ps.mean());
     System.out.println("stddev: " + ps.stddev());
     System.out.println("confidence interval: [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
   }
}
