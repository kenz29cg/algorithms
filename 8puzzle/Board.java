import java.lang.Math;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import  java.lang.StringBuilder;
import java.lang.IllegalArgumentException;

public class Board {

    private int[][] grid;
    private int n;
    
    // create a grid from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("Null input.");
        
        n = tiles.length;
        grid = new int[n][n];
        
	for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++)
        grid[i][j] = tiles[i][j];
	}
    }
                                           
    // string representation of this grid
    public String toString() {
        StringBuilder str = new StringBuilder(String.valueOf(n));
        str.append("\n");
            
        //String str = String.valueOf(n) + "\n";

        for (int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++){
                str.append(String.valueOf(grid[i][j]));
                str.append(" ");
            }
            if (i != n-1)
                str.append("\n");
        }
        return str.toString();
    }

    // grid dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int dist = 0;
        // int[][] goal = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i*j != (n-1)*(n-1) && grid[i][j] != i*n+(j+1))
                    dist++;
            }
        }
        return dist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // (i,j) vs (value/n, (value%n)-1)
                if (grid[i][j] != 0) {
                    int remainder = grid[i][j] % n;
                    if (remainder == 0){
                        dist  = dist + Math.abs(i - (grid[i][j]/n-1)) + Math.abs(j - (n-1));
                    }
                    else {
                        dist = dist + Math.abs(i - grid[i][j]/n) + Math.abs(j - (grid[i][j] % n - 1)); 
                    }
                }
            }
        }
        return dist;
    }

    // is this grid the goal grid?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this grid equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board that = (Board)y;
        
        if (this.dimension() != that.dimension()) return false;

        // for(Board nb : this.neighbors()) {
        //     for (Board nb2 : nb.neighbors()) {
        //         if (y.equals(nb2)) return true;
        //     }
        // }
        // return false;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if (this.grid[i][j] != that.grid[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring grids
    public Iterable<Board> neighbors() {
        ArrayList<Board> nb = new ArrayList<Board>();
        Board boardU = new Board(grid);
        Board boardD = new Board(grid);
        Board boardL = new Board(grid);
        Board boardR = new Board(grid);
        
        int[] blankPos = findblank();
        int x = blankPos[0];
        int y = blankPos[1];

        if (boardL.swap(x, y, x-1, y)) nb.add(boardL);
        if (boardR.swap(x, y, x+1, y)) nb.add(boardR);
        if (boardU.swap(x, y, x, y-1)) nb.add(boardU);
        if (boardD.swap(x, y, x, y+1)) nb.add(boardD);

        return nb;        
    }

    private int[] findblank() {
        int[] pos = new int[2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    pos[0] = i;
                    pos[1] = j;
                    return pos;
                }
            }
        }
        return pos;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board newboard = new Board(grid);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {
                if (grid[i][j] != 0 && grid[i][j+1] != 0) {
                    newboard.swap(i, j, i, j+1);
                    return newboard;
                }
            }
        }
        return newboard;
    }

    private boolean swap(int a, int b, int c, int d) {
        if (a < 0 || c < 0 || b >= n || d >= n || a >= n || b < 0 || c >= n || d < 0) {
            return false;
        }
        int temp = grid[a][b];
        grid[a][b] = grid[c][d];
        grid[c][d] = temp;
        return true;
    }


    // unit testing (not graded)
	public static void main(String[] args) {
        int n = StdIn.readInt();
        
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            tiles[i][j] = StdIn.readInt();
        Board initial = new Board(tiles);   
        StdOut.println(initial);
        StdOut.println(initial.twin());
        StdOut.println("Equals to self: " + initial.equals(initial));
        StdOut.println("findblank: " + initial.findblank()[0] + " " + initial.findblank()[1]);
        StdOut.println("Manhattan: " + initial.manhattan());
        StdOut.println("Hamming: " + initial.hamming());
        StdOut.println("Isgoal: " + initial.isGoal());
        for (Board b : initial.neighbors())
            StdOut.println(b);
        
    }

}
