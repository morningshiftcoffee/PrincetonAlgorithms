package Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// Algorithms I Module 2 Assignment: Percolation
// https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
public class Percolation {

    // n-by-n grid
    private boolean[][] grid;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufBackwash;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should not be smaller than 1.");
        }

        this.grid = new boolean[n][n];
        this.numberOfOpenSites = 0;

        uf = new WeightedQuickUnionUF(n * n + 2);
        // backwash uf doesn't have virtual bottom node
        ufBackwash = new WeightedQuickUnionUF(n * n + 1);
    }

    // Validate if row number and col number are valid
    private void validateRowAndCol(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("row should be between 1 and n.");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("col should be between 1 and n.");
        }
    }

    private int convertRowAndColToIndexOfSites(int row, int col) {
        return grid.length * (row - 1) + col;
    }

    // opens the site (row, col) if it is not open already
    // Also union the site with 4 surrounding sites: top, bottom, left, right
    public void open(int row, int col) {
        validateRowAndCol(row, col);

        if (grid[row - 1][col - 1]) {
            return;
        }

        grid[row - 1][col - 1] = true;
        numberOfOpenSites += 1;

        int indexOfOpenSite = convertRowAndColToIndexOfSites(row, col);

        // connect first row with virtual top node
        if (row == 1) {
                uf.union(0, indexOfOpenSite);
                ufBackwash.union(0, indexOfOpenSite);
        }
        if (row == grid.length) {
            uf.union(grid.length * grid.length + 1, indexOfOpenSite);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            int indexOfTopSite = convertRowAndColToIndexOfSites(row - 1, col);
            uf.union(indexOfTopSite, indexOfOpenSite);
            ufBackwash.union(indexOfTopSite, indexOfOpenSite);
        }
        if (row < grid.length && isOpen(row + 1, col)) {
            int indexOfBottomSite = convertRowAndColToIndexOfSites(row + 1, col);
            uf.union(indexOfBottomSite, indexOfOpenSite);
            ufBackwash.union(indexOfBottomSite, indexOfOpenSite);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            int indexOfLeftSite = convertRowAndColToIndexOfSites(row, col - 1);
            uf.union(indexOfLeftSite, indexOfOpenSite);
            ufBackwash.union(indexOfLeftSite, indexOfOpenSite);
        }
        if (col < grid.length && isOpen(row, col + 1)) {
            int indexOfRightSite = convertRowAndColToIndexOfSites(row, col + 1);
            uf.union(indexOfRightSite, indexOfOpenSite);
            ufBackwash.union(indexOfRightSite, indexOfOpenSite);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowAndCol(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowAndCol(row, col);
        return ufBackwash.find(convertRowAndColToIndexOfSites(row, col)) == ufBackwash.find(0) && isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(0) == uf.find(grid.length * grid.length + 1)) {
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
