
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final WeightedQuickUnionUF uf;
  private final int n;
  private boolean[][] matrix;
  private int numberOpenSites;

  // create n-by-n grid, with all sites blocked
   public Percolation(int n) {
     if (n<1) throw new java.lang.IllegalArgumentException();
     uf = new WeightedQuickUnionUF(n*n); //from 0 to n-1
     this.n = n;
     this.matrix = new boolean[n][n]; //initialize with zeros
   }

   // open site (row, col) if it is not open already
   public    void open(int row, int col) {

     if (row<1 || row>n || col<1|| row>n) throw new java.lang.IllegalArgumentException();
     if(!isOpen(row, col)) {

         int x = row-1;
         int y = col-1;
         int xy = index2d_to_index1d(x, y);

         matrix[x][y] = true;
         ++numberOpenSites;

         if (x-1 >= 0 && matrix[x-1][y]) uf.union(xy, index2d_to_index1d(x-1, y));
         if (x+1 < n  && matrix[x+1][y]) uf.union(xy, index2d_to_index1d(x+1, y));
         if (y-1 >= 0 && matrix[x][y-1]) uf.union(xy, index2d_to_index1d(x, y-1));
         if (y+1 < n  && matrix[x][y+1]) uf.union(xy, index2d_to_index1d(x, y+1));
    }
   }

   // is site (row, col) open?
   public boolean isOpen(int row, int col) {
     if (row<1 || row>n || col<1|| row>n) throw new java.lang.IllegalArgumentException();
     return matrix[row-1][col-1];
   }

   private int index2d_to_index1d(int row, int col) {
     return col+n*row;
   }

   // is site (row, col) full?
   public boolean isFull(int row, int col) {
     for(int i = 0;i < n; ++i) {
       if(isOpen(1,i+1)) {
         if(uf.connected(index2d_to_index1d(row-1, col-1), i))
          return true;
       }
     }
     return false;
   }

   private boolean isConnected(int a, int b, int c, int d) {
     int position_ab = a-1 + n*(b-1);
     int position_cd = c-1 + n*(d-1);
     return uf.connected(position_ab, position_cd);
   }

   // number of open sites
   public     int numberOfOpenSites() {
     return numberOpenSites;
   }

   // does the system percolate?
   public boolean percolates() {
     for (int i = 1; i<=n; ++i ) {
       if(isOpen(n,i)) {
          if(isFull(n, i))
            return true;
       }
     }
     return false;
   }

// test client (optional)
   public static void main(String[] args) {
     Percolation per = new Percolation(4);
     System.out.println("numberOpenSites: " + per.numberOfOpenSites());
     per.open(1, 1);
     System.out.println("numberOpenSites: " + per.numberOfOpenSites());
     per.open(4, 4);
     System.out.println("numberOpenSites: " + per.numberOfOpenSites());

     System.out.println(per.isFull(1, 1));
     System.out.println(per.isFull(4, 4));

     System.out.println(per.isConnected(4, 1, 1, 2));
     System.out.println(per.isFull(4, 1));

     System.out.println(per.percolates());
   }
}
