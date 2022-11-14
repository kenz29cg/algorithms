import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){
	int k = Integer.parseInt(args[0]);
	RandomizedQueue<String> rQ = new RandomizedQueue<String>();
	while(!StdIn.isEmpty()){
	    rQ.enqueue(StdIn.readString());
	}
	Iterator<String> i = rQ.iterator();
	int j = 0;
	while (i.hasNext() && j < k){ String s = i.next(); StdOut.println(s); j++; }
	// for(String s : rQ){ StdOut.println(s); }
    }
}
