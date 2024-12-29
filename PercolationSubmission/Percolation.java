import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF quickUnion;
    private int n;
    private boolean[] isOpenArr;
    private int numOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n needs to be > 0");
        }
        this.n = n;
        // Adds 2 extra sites as virtual sites on the top and bottom of grid
        this.quickUnion = new WeightedQuickUnionUF((n * n) + 2);
        this.isOpenArr = new boolean[n * n];
        this.numOpen = 0;
        // Union virtual top and bottom sites to top and bottom rows
        for (int col = 1; col <= this.n; col++) {
            this.quickUnion.union(0, col);
            this.quickUnion.union((n * n) + 1, n * (n - 1) + col);
        }
    }

    private int coordToN(int row, int col) {
        // Converts row, col to position n in array accounting for virtual sites at
        // beginning and end
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("The (row, col) indices are out of the bounds [1,n]");
        }
        return ((row - 1) * this.n) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (this.isOpen(row, col)) {
            return;
        }
        // Unions site to all adjacent sites
        if (row > 1 && this.isOpen(row - 1, col)) {
            this.quickUnion.union(coordToN(row - 1, col), coordToN(row, col));
        }
        if (col > 1 && this.isOpen(row, col - 1)) {
            this.quickUnion.union(coordToN(row, col - 1), coordToN(row, col));
        }
        if (row < this.n && this.isOpen(row + 1, col)) {
            this.quickUnion.union(coordToN(row + 1, col), coordToN(row, col));
        }
        if (col < this.n && this.isOpen(row, col + 1)) {
            this.quickUnion.union(coordToN(row, col + 1), coordToN(row, col));
        }
        this.isOpenArr[coordToN(row, col) - 1] = true;
        this.numOpen++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.isOpenArr[coordToN(row, col) - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return (this.isOpen(row, col) && (this.quickUnion.find(0) == this.quickUnion.find(coordToN(row, col))));

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return (this.quickUnion.find(0) == this.quickUnion.find((this.n * this.n) + 1));
    }

    // test client (optional)
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int n = 100;
        int trials = 1000;
        double[] numOpenToPercolate = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolationClient = new Percolation(n);
            boolean percolates = false;
            while (!percolates) {
                percolationClient.open(StdRandom.uniformInt(1, n + 1), StdRandom.uniformInt(1, n + 1));
                percolates = percolationClient.percolates();
                if (percolates) {
                    numOpenToPercolate[i] = percolationClient.numberOfOpenSites() / (double) (n * n);
                }
            }
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.printf("Percolation simulation took: %d ms\n", endTime);
        // System.out.println(Arrays.toString(numOpenToPercolate));
        double mean = StdStats.mean(numOpenToPercolate);
        System.out.println("Percolation mean is: " + mean);
        double standardDeviation = StdStats.stddev(numOpenToPercolate);
        System.out.println("Percolation standard deviation is: " + standardDeviation);
    }
}