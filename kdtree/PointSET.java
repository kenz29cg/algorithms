import java.util.TreeSet;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.StdOut;



public class PointSET {

    private int size = 0;
    private TreeSet<Point2D> points;
    
    public PointSET() {                               // construct an empty set of points 
        points = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {                        // is the set empty? 
        return (size == 0);
    }                     

    public int size() {                               // number of points in the set 
        return size;
    }
    
    public void insert(Point2D p) {                   // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException("No point.");
        if (points == null) { points = new TreeSet<Point2D>(); points.add(p); size = 1;}
        else if (!points.contains(p)) {
            points.add(p);
            size++;
        }
    }    
    
    public boolean contains(Point2D p){               // does the set contain point p?
        if (p == null) throw new IllegalArgumentException("No point read.");
        if (size == 0) return false;
        else return points.contains(p);

    }
    public void draw() {                             // draw all points to standard draw
        // this.isIllegal();
        for (Point2D currentP : points){
           double x = currentP.x();
           double y = currentP.y();
           StdDraw.point(x, y);
                }
    }
    
    public Iterable<Point2D> range(RectHV rect) {     // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException("No rectangle read.");
        if (this.isEmpty()) return null;
        ArrayList<Point2D> inRect = new ArrayList<Point2D>();
        for (Point2D currentP : points){
            if (rect.contains(currentP))
                inRect.add(currentP);
        }
        return inRect;
    }
    public Point2D nearest(Point2D p) {              // a nearest neighbor in the set to point p; null if the set is empty
        // this.isIllegal();
        if (p == null) throw new IllegalArgumentException("No point read.");
        if (size == 0) throw new IllegalArgumentException("Null point set.");
        Point2D champion = points.first();
        // StdOut.println("Show first by comparator: " + champion.toString());
        double dist;
        for (Point2D currentP : points){
            if (champion.distanceSquaredTo(p) > currentP.distanceSquaredTo(p))
                champion = currentP;
        }
        return champion;               
    }
    // private void isIllegal() {
    //     if (points == null)
    //         throw new IllegalArgumentException("Set is null.");
    // }
        
    public static void main(String[] args) {         // unit testing of the methods (optional)
        PointSET set = new PointSET();
        StdOut.println("Point set is empty? " + set.isEmpty());
        Point2D p0 = new Point2D(0,0);
        Point2D p1 = new Point2D(0.1,0.1);
        Point2D p2 = new Point2D(0.2,0.1);
        Point2D p3 = new Point2D(0.3,0.1);
        Point2D p4 = new Point2D(0.4,0.1);
        Point2D p5 = new Point2D(0.1,0.5);
        set.insert(p1);
        set.insert(p2);
        set.insert(p3);
        set.insert(p4);
        set.insert(p5);
        StdOut.println("Point set has size? " + set.size());
        Point2D p6 = new Point2D(0.2,0.35);
        StdOut.println("Point set contains (0.2,0.35)? " + set.contains(p6));
        set.draw();
        RectHV rect = new RectHV(0,0,0.4,0.4);
        StdOut.println("Point set in rectangle? ");
        for (Point2D p : set.range(rect))
            StdOut.println(p.toString());
        StdOut.println("Point set nearest to (0,0)? " + set.nearest(p0));
    }  
}
