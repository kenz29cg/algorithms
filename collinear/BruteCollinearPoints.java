import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class BruteCollinearPoints {
    //private Point[] pointArray;
    private int m = 0; // linesegment group size
    private ArrayList<LineSegment> lineSegment = new ArrayList<LineSegment>();
    
    public BruteCollinearPoints(Point[] points){    // finds all line segments containing 4 points
        if (points == null)
	    throw new IllegalArgumentException("No points.");
	
	int n = points.length;
	
	Point[] save = points.clone();
	Arrays.sort(save);
	if (repeatedPoints(save))
	    throw new IllegalArgumentException("Contains repeated points.");


	//pointArray = new Point[n];
	if (n <= 3) {throw new IllegalArgumentException("Not enough points."); }
	for (int i = 0; i < n-3; i++){
	    for (int j = i+1; j < n-2; j++){
		for (int k = j+1; k < n-1; k++){
		    for (int p = k+1; p < n; p++){
			if (save[i] == null || save[j] == null || save[k] == null || save[p] == null) {
            throw new IllegalArgumentException("Contains null points.");
			}
			//	else if (points[i] == points[j] || points[i] == points[k] || points[i] == points[p] || points[j] == points[k] || points[j] == points[p] || points[k] == points[p]){
			// throw new IllegalArgumentException("Contains repeated points.");
			//	}
			else{
			    double slope1 = save[i].slopeTo(save[j]);
			    double slope2 = save[i].slopeTo(save[k]);
			    double slope3 = save[i].slopeTo(save[p]);
                            if (slope1 == slope2 && slope2 == slope3){
				m++;
				lineSegment.add(new LineSegment(save[i], save[p]));
                                //pointArray[4*(m-1)] = points[i];
				//pointArray[4*(m-1) + 1] = points[j];
				//pointArray[4*(m-1) + 2] = points[k];
				//pointArray[4*(m-1) + 3] = points[p];
			    }
			}
		    }
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
    
    public int numberOfSegments(){ return m; }        // the number of line segments
    
    public LineSegment[] segments(){                // the line segments
	/*
	LineSegment[] lineSegment = new LineSegment[m];
	for (int i = 0; i < m; i++){
	    Point min = pointArray[4*i];
	    Point max = pointArray[4*i];
	    for (int j = 1; j < 4; j++){
		
		if (pointArray[4*i+j].compareTo(min) < 0) min = pointArray[4*i+j];
		if (pointArray[4*i+j].compareTo(max) > 0) max = pointArray[4*i+j];
		    }x		   
	    lineSegment[i] = new LineSegment(min, max);
	}
	return lineSegment;
	*/
	return lineSegment.toArray(new LineSegment[lineSegment.size()]);
    }


    public static void main(String[] args) {
	Point p1 = new Point(1,1);
	Point p2 = new Point(1,2);
	Point p3 = new Point(2,2);
	Point p4 = new Point(3,3);
	Point p5 = new Point(4,4);
	Point[] points = {p1,p2,p3,p4,p5};
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
	LineSegment[] lsg = bcp.segments();
	StdOut.println(lsg[0].toString());
    }
}
