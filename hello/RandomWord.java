import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord
{   
    public static void main(String[] args){
	String s = null;
	String t = null;
        int i = 0;
	double p = 0;
	while (!StdIn.isEmpty()) {
	    t = StdIn.readString();
	    ++i;
	    p = 1d/i;
            if(StdRandom.bernoulli(p)){
              s = t;
             }
	}
	System.out.println(s);
    }
}
