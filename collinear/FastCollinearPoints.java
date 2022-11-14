import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Arrays;


public class FastCollinearPoints {
    
    //private int m = 0; // linesegment group size
    //private LineSegment[] lineSegment;
    private ArrayList<LineSegment> lineSegment = new ArrayList<LineSegment>();

    
    public FastCollinearPoints(Point[] points){     // finds all line segments containing 4 or more points
	
	if (points == null) {throw new IllegalArgumentException("No points."); }
	
	int n = points.length;
	//lineSegment = new LineSegment[n];

	for (int i = 0;i <n; i++)
	    if (points[i] == null)
		{ throw new IllegalArgumentException("Contains null points.");}
      
	Point[] save = points.clone();

	if (repeatedPoints(save))
		    { throw new IllegalArgumentException("Contains repeated points."); }
	
	for (int i = 0; i < n; i++){

	    //StdOut.println("i = " + i);
	    //StdOut.println("m = " + m);
	    //if (n-i <= 2) break;

	    //StdOut.println("save[i]: " + save[i].toString());
	    //Arrays.sort(points);
	    //sort(points, save[i]);
	    Arrays.sort(points, save[i].slopeOrder());
	    
		//StdOut.println("Sorted points: " + points[a].toString());
	    for (int j = 1; j < n-2; j++){
		//StdOut.println("First point: " + points[0].toString());
		//StdOut.println("save[i]" + save[i].toString());
		//StdOut.println("points[0]" + points[0].toString());
		//StdOut.println("first slope = " + points[0].slopeTo(save[i]));
                
		double currentSlope = points[j].slopeTo(save[i]);
		//StdOut.println("current slope: " + currentSlope);
                Point min = save[i];
		Point max = save[i];
		//StdOut.println("min = " + min);
		//StdOut.println("max = " + max);
		int k = 0;
		while (j+k < n && points[j+k].slopeTo(save[i]) == currentSlope) {
		    //StdOut.println("this slope = " + points[j+k].slopeTo(save[i]));
		    //StdOut.println("points[j+k]= " + points[j+k].toString());
		    if (points[j+k].compareTo(min) < 0) min = points[j+k];
		    if (points[j+k].compareTo(max) > 0) max = points[j+k];
		    k++;
		    //StdOut.println("k = " + k);
		}
		//StdOut.println("min = " + min);
		//StdOut.println("max = " + max);
		if (k >= 3 && min.compareTo(save[i]) == 0) {
		    LineSegment minmax = new LineSegment(min,max);
		    if (!lineSegment.contains(minmax))
		    lineSegment.add(minmax);
		}

	    }
	}
    }


    private boolean repeatedPoints(Point[] points){
	
	for (int i = 0; i < points.length-1; i++){
	    if (points[i].compareTo(points[i+1]) == 0) return true;
	}
	return false;
    }

    /*
    private static void merge(Point[] points, Point[] aux, int lo, int mid, int hi, Point p){
	for (int k = lo; k <= hi; k++)
	    aux[k] = points[k];

	int i = lo, j = mid+1;
	for (int k = lo; k <= hi; k++){
	    if (i > mid) points[k] = aux[j++];
	    else if (j > hi) points[k] = aux[i++];
	    else if (p.slopeOrder().compare(aux[j],aux[i]) < 0) points[k] = aux[j++];
	    else points[k] = aux[i++]; 
	}
    }

    
    private static void sort(Point[] points, Point[] aux, int lo, int hi, Point p){
        if (hi <= lo) return;
	int mid = lo+(hi-lo)/2;
	//StdOut.println("lo = " + lo);
	//StdOut.println("hi = " + hi);
	//StdOut.println("mid = " + mid);
	sort(points, aux, lo, mid, p);
	sort(points, aux, mid+1, hi, p);
	merge(points, aux, lo, mid, hi, p);
    }

    private static void sort(Point[] points, Point p){
	Point[] aux = new Point[points.length];
	sort(points, aux, 0, points.length-1, p);
    }
    */
    public int numberOfSegments(){       // the number of line segments
	//return m;
	return lineSegment.size();
    }
    public LineSegment[] segments(){           // the line segments
	//LineSegment[] ls = new LineSegment[m];
	//for (int i = 0; i < m; i++) { ls[i] = lineSegment[i]; }
	
	//return ls;
	return lineSegment.toArray(new LineSegment[lineSegment.size()]);
    }
           
      public static void main(String[] args) {

	  Point p1 = new Point(1,1);
	Point p2 = new Point(1,2);
	Point p3 = new Point(2,2);
	Point p4 = new Point(3,3);
	Point p5 = new Point(4,4);
	Point[] points = {p1,p2,p3,p4,p5};
        FastCollinearPoints bcp = new FastCollinearPoints(points);
	int m = bcp.numberOfSegments();
	StdOut.println(m);
	LineSegment[] ls = bcp.segments();
	StdOut.println(ls[0].toString());

      }

    

}
