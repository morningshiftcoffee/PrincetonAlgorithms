package Percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    // Create an array to record trial results.
    // The result is the number of open sites when sites are percolated
    private double[] results;

    // Perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials should be larger than 0.");
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            // StdOut.println("======================================");
            // StdOut.printf("Starting trial %d \n\n", i);
            int result = performSingleTrial(n);
            results[i] = (double) result / (double) (n * n);
            // StdOut.println("======================================");
        }
    }

    private int performSingleTrial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int rowToOpen = StdRandom.uniformInt(n) + 1;
            int colToOpen = StdRandom.uniformInt(n) + 1;
            // StdOut.printf("Opening site row %d col %d \n", rowToOpen, colToOpen);
            if (percolation.isOpen(rowToOpen, colToOpen)) {
                // StdOut.printf("Site row %d col %d is already opened. \n\n", rowToOpen, colToOpen);
                continue;
            }
            percolation.open(rowToOpen, colToOpen);
            // StdOut.printf("Successfully opened site row %d col %d \n", rowToOpen, colToOpen);
            // StdOut.printf("Number of opened sites %d \n\n", percolation.numberOfOpenSites());
        }
        return percolation.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t);

        StdOut.printf("mean = %.6f\n", stats.mean());
        StdOut.printf("stddev = %.6f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%.6f, %.6f]\n",
                stats.confidenceLo(),
                stats.confidenceHi());
    }
}
