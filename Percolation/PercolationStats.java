import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private double mean = 0.0;
    private double sd = 0.0;
    private double interval = 0.0;
    
    public PercolationStats(int n, int trials)
    {
	if((n <= 0) || (trials <= 0)) throw new IllegalArgumentException ("Grid number and number of trials must be positive.");
	
        int fullnumber = 0;
	double[] estimates = new double[trials];

	double thresholdsum = 0.0;
	double sdsum = 0.0;
	
	for (int j = 0; j < trials; j++)
	    {
		Percolation P = new Percolation(n);
	        while(!P.percolates())
		    {
			int col = StdRandom.uniformInt(1, n+1);
			int row = StdRandom.uniformInt(1, n+1);
			if (!P.isOpen(row, col)) P.open(row, col);
		    }
		fullnumber = P.numberOfOpenSites();
		//StdOut.println("fullnumber = " + fullnumber);
		double Fullnumber = fullnumber;
		estimates[j] = Fullnumber / (n*n);
		thresholdsum += estimates[j];
		//StdOut.println("thresholdsum" + thresholdsum);
	    }
	mean = StdStats.mean(estimates);
	//StdOut.println("mean = " + mean);
	//  for (int j = 0; j< trials; j++)
	  //  {
	//  sdsum += (estimates[j] - mean)*(estimates[j] - mean);
// }
    
	sd = StdStats.stddev(estimates);
	interval = 1.96 * Math.sqrt(sd) / Math.sqrt(trials);
    }

    public double mean()
    {
	return mean;	
    }

    public double stddev()
    {
	return Math.sqrt(sd);
    }

    public double confidenceLo()
    {
	return (mean - interval);
    }

    public double confidenceHi()
    {
	return (mean + interval);
    }

    public static void main (String[] args)
    {
	int grid = StdIn.readInt();
	int T = StdIn.readInt();
	PercolationStats PS = new PercolationStats(grid, T);
	StdOut.println("mean = " + PS.mean());
	StdOut.println(PS.stddev());
	StdOut.println("95% confidence interval = [" + PS.confidenceLo() + ", " + PS.confidenceHi() + "]");
    }
}
