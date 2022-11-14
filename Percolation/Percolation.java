import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
// import java.lang.Math;


public class Percolation {

    private boolean[][] a;
    private WeightedQuickUnionUF uf;
    
    public Percolation(int n)
    {
	if (n <= 0) throw new IllegalArgumentException("N should be positive.");
        a = new boolean[n][n];
        for (int i = 0; i < n; i++)
	    { for (int j = 0; j < n; j++)
		a[i][j] = false;
	    }
	uf = new WeightedQuickUnionUF(n*n + 2);
    }



    private boolean isAvailable(int row, int col)
    {
	int n = a.length;
	int r = row - 1;
	int c = col - 1;
	if (r >= 0 && c >= 0 && r < n && c < n){ return true; }
	else { return false; }
    }

    private void rowcolcheck(int row, int col)
    {
	if (!isAvailable(row, col)) { throw new IllegalArgumentException("Index ouf of bounds.");}
    }
    
    
    public void open(int row, int col)
    {
	rowcolcheck(row, col);
	if (a[row-1][col-1]) { return; }
	else{
	    a[row-1][col-1] = true;
	    int n = a.length;
	    int position = 2+(row-1)*n+col-1;

	    if (row == 1)
		{
		     uf.union(0, position);
		}
	    if (row == n )
		{
		     uf.union(1, position);
		}
	    if (isAvailable(row-1,col) && isOpen(row-1, col))
		{
		    uf.union(position-n, position);
		}
	    if (isAvailable(row+1,col) && isOpen(row+1, col))
		{
		    uf.union(position+n, position);
		}
	    if (isAvailable(row,col-1) && isOpen(row, col-1))
		{
		    uf.union(position-1, position);
		}
	    if (isAvailable(row,col+1) && isOpen(row, col+1))
		{
		    uf.union(position+1, position);
		}
	    /*if (row == 1)
		{
		    uf.union(0, position);
		    if (col == 1)
			{
			    if (a[row-1][col]) uf.union(position, position+1);
			    if (a[row][col-1]) uf.union(position, position+n);
			}
		    else if (col == n)
			{
			    if (a[row-1][col-2]) uf.union(position, position-1);
			    if (a[row][col-1]) uf.union(position, position+n);
			}
			
		}
	    else if (row == n)
		{
		    uf.union(1, position);
		    if (col == 1)
			{
			    if (a[row-1][col]) uf.union(position, position+1);
			    if (a[row-2][col-1]) uf.union(position, position-n);
			}
		    else if (col == n)
			{
			    if (a[row-1][col-2]) uf.union(position, position-1);
			    if (a[row-2][col-1]) uf.union(position, position-n);
			}
		    else
			{
			    if (a[row-2][col-1]) uf.union(position, position-n);
			    if (a[row-1][col]) uf.union(position, position+1);
			    if (a[row-1][col-2]) uf.union(position, position-1);
			}
		}
	    else if(col == 1)
		{
		    if (a[row-2][col-1]) uf.union(position, position-n);
		    if (a[row][col-1]) uf.union(position, position+n);
		    if (a[row-1][col]) uf.union(position, position+1);
		}
	    else if (col == n)
		{
		    if (a[row-2][col-1]) uf.union(position, position-n);
		    if (a[row][col-1]) uf.union(position, position+n);
		    if(a[row-1][col-2]) uf.union(position, position-1);
		}
	    else
		{
		    if (a[row-2][col-1]) uf.union(position, position-n);
		    if (a[row][col-1]) uf.union(position, position+n);
		    if (a[row-1][col-2]) uf.union(position, position-1);
		    if (a[row-1][col]) uf.union(position, position+1);
		}
	    */
	}
    }

    public boolean isOpen(int row, int col)
    {
        rowcolcheck(row, col);
	if (a[row-1][col-1]) { return true; }
	else { return false; }
    }

    public boolean isFull(int row, int col)
    {
	rowcolcheck(row, col);
	int n = a.length;
	int position = 2+(row-1)*n+col-1;
        return uf.find(0) == uf.find(position);
    }

    public int numberOfOpenSites()
    {
	int n = a.length;
	int number = 0;
	for(int i = 0; i < n; i++)
	    { for(int j = 0; j < n; j++)
		    { if(a[i][j])
			    number += 1;}
	    }
	return number;
    }

    public boolean percolates()
    {
        boolean r = (uf.find(0) == uf.find(1));
	return r;
    }

    /* private void runTests() {
        for (int row = 1; row <= gridSize; row++) {
            for (int col = 1; col <= gridSize; col++) {
                if (isOpen(row, col)) {
                    StdOut.printf("Row: %d Col: %d is Open %n", row, col);
                } else {
                    StdOut.printf("Row: %d Col: %d is not Open %n", row, col);
                }
                if (isFull(row, col)) {
                    StdOut.printf("Row: %d Col: %d is Full %n", row, col);
                } else {
                    StdOut.printf("Row: %d Col: %d is not Full %n", row, col);
                }
            }
        }
*/
    public static void main(String[] args)
    {
	int n = Integer.parseInt(args[0]);
	StdOut.println("matrix" + n);
	int count = args.length;
	StdOut.println("length" + count);
	Percolation P = new Percolation(n);
	for (int i = 1; count >= 3; i += 2)
	    {
		int row = Integer.parseInt(args[i]);
		int col = Integer.parseInt(args[i+1]);
		
		P.open(row, col);
		StdOut.println("(" + row + ", " + col + ")");
		StdOut.println("(" + row + ", " + col + ")" + P.isOpen(row, col));
		StdOut.println("(" + row + ", " + col + ")" + P.isFull(row, col));
		
		if (P.percolates()) { StdOut.println("It percolates.");}
		count -= 2;
	    }
	if (!P.percolates()) StdOut.println("It doesn't percolate.");
	
    }
}
