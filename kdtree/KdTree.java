import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.StdOut;
// import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;


public class KdTree {

    private Node root;
    private Point2D champion;

    private int size = 0;
    
    private class Node {
        Node left;
        Node right;
        double x;
        double y;
        boolean level = true; // true when level is even
        int count;
        public Node(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public Node() {
            this.count = 0;
        }    
    }

    private boolean isLeft(Node n1) {
        return (!(n1.left == null));
    }
    
    private boolean isRight(Node n1) {
        return (!(n1.right == null));
    }
    
    // construct an empty set of points 
    public KdTree() {
        root = null;
        size = 0;
    }
    
    // is the set empty? 
    public boolean isEmpty() {  
        return (size == 0);
    }
    
    // number of points in the set 
    public int size() { 
        return size;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("No point read.");
        if (root == null) {
            root = new Node();
            root.x = p.x();
            root.y = p.y();
            root.count = 1;
            root.level = true;
            size = 1;
            //StdOut.println("size = " + size);
        }
        else { Node parent = findParent(root, p); }
    }
    //private int c = 0;
    private Node findParent(Node n1, Point2D p) {
        //StdOut.println("Point inserted is : " + p.toString());
        //StdOut.println("Current node is : " + n1.x + ", " + n1.y);
        //c++;
        //StdOut.println("size = " + size + "c = " + c);
        double px = p.x();
        double py = p.y();
        
        if (px == n1.x && py == n1.y) {
            return n1;
        }
        else {            
            if (n1.level) {
                if (px < n1.x && this.isLeft(n1)) {
                        n1.count++;
                        return findParent(n1.left, p);
                }
                else if (px < n1.x && !this.isLeft(n1)) {
                    n1.count++;
                    Node newNode = new Node();
                    newNode.x = px;
                    newNode.y = py;
                    newNode.level = !n1.level;
                    newNode.count = 1;
                    n1.left = newNode;
                    size++;
                    return n1;
                }
                else if (px >= n1.x && this.isRight(n1)) {
                    n1.count++;
                    return findParent(n1.right, p);
                }
                else {
                    n1.count++;
                    Node newNode = new Node();
                    newNode.x = px;
                    newNode.y = py;
                    newNode.level = !n1.level;
                    newNode.count = 1;
                    n1.right = newNode;
                    size++;
                    return n1;
                }
            }
            else {
                if (py < n1.y && this.isLeft(n1)) {
                    n1.count++;
                    return findParent(n1.left, p);
                }
                else if (py < n1.y && !this.isLeft(n1)) {
                    n1.count++;
                    Node newNode = new Node();
                    newNode.x = px;
                    newNode.y = py;
                    newNode.level = !n1.level;
                    newNode.count = 1;
                    n1.left = newNode;
                    size++;
                    return n1;
                }
                else if (py >= n1.y && this.isRight(n1)) {
                    n1.count++;
                    return findParent(n1.right, p);
                }
                else {
                    n1.count++;
                    Node newNode = new Node();
                    newNode.x = px;
                    newNode.y = py;
                    newNode.level = !n1.level;
                    newNode.count = 1;
                    n1.right = newNode;
                    size++;
                    return n1;
                }
            }
        }
    }
    
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        // this.isIllegal();
        if (p == null) throw new IllegalArgumentException("No point read.");
        if (root == null) return false;
        else {
            Node parent = findParent(root, p);
            //StdOut.println("Point tested is : " + p.toString());
            //StdOut.println("Current node is : " + parent.x + ", " + parent.y);
            if (parent.x == p.x() && parent.y == p.y()) return true;
            else if (parent.level) {
                if (p.x() < parent.x) parent.left = null;
                else parent.right = null;
                return false;
            }
            else {
                if (p.y() < parent.y) parent.left = null;
                else parent.right = null;
                return false;
            }
        }
    }


    // draw all points to standard draw 
    public void draw() {
        this.draw(root, 0.0, 0.0, 1.0, 1.0);
    }

    private void draw(Node nd, double xl, double yl, double xu, double yu) {
        if (nd == null) return;
        
        if (nd.level) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(nd.x, nd.y);
            
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(nd.x, yl, nd.x, yu);
            draw(nd.left, xl, yl, Math.min(nd.x, xu), yu);
            draw(nd.right, Math.max(xl, nd.x), yl, xu, yu);
        }
        else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(nd.x, nd.y);
            
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xl, nd.y, xu, nd.y);
            draw(nd.left, xl, yl, xu, Math.min(nd.y, yu));
            draw(nd.right, xl, Math.max(yl, nd.y), xu, yu);
        } 
    }
    
    // all points that are inside the rectangle (or on the boundary)

    public Iterable<Point2D> range(RectHV rect) {
        // this.isIllegal();
        ArrayList<Point2D> q = new ArrayList<Point2D>();
        if (rect == null) throw new IllegalArgumentException("No rectangle read.");
        nodeAdding(root, 0.0, 0.0, 1.0, 1.0, rect, q);        
        return q;
    }

    private void nodeAdding(Node nd, double xl, double yl, double xu, double yu, RectHV rect, ArrayList<Point2D> q) {
        if (nd == null) return;
        Point2D point = new Point2D(nd.x, nd.y);
        if (rect.contains(point)) q.add(point);
        if (nd.level) {
            RectHV pointRect1 = new RectHV(xl, yl, Math.min(nd.x, xu), yu);
            if (rect.intersects(pointRect1)) nodeAdding(nd.left, xl, yl, Math.min(nd.x, xu), yu, rect, q);
            RectHV pointRect2 = new RectHV(Math.max(xl, nd.x), yl, xu, yu);
            if (rect.intersects(pointRect2)) nodeAdding(nd.right, Math.max(xl, nd.x), yl, xu, yu, rect, q);
        }
        else {
            RectHV pointRect3 = new RectHV(xl, yl, xu, Math.min(nd.y, yu));
            if (rect.intersects(pointRect3)) nodeAdding(nd.left, xl, yl, xu, Math.min(nd.y, yu), rect, q);
            RectHV pointRect4 = new RectHV(xl, Math.max(yl, nd.y), xu, yu);
            if (rect.intersects(pointRect4)) nodeAdding(nd.right, xl, Math.max(yl, nd.y), xu, yu, rect, q);
        }
    }
    
    
    
    // private void isIllegal() {
    //     if (root == null)
    // //         throw new IllegalArgumentException("Set is null.");
    // // } 

    // private boolean isInRect(RectHV rect, Node nd) {
    //     if (nd == null) return false;
    //     else if (nd.x <= rect.xmax() && nd.x >= rect.xmin() && nd.y <= rect.ymax() && nd.y >= rect.ymin())
    //         return true;
    //     else return false;
    // }
    
    // a nearest neighbor in the set to point p; null if the set is empty

    public Point2D nearest(Point2D p) {
        // this.isIllegal();
        if (p == null) throw new IllegalArgumentException("No point read.");
        if (root == null) throw new IllegalArgumentException("Point set empty.");
        champion = new Point2D(root.x, root.y);
        checkingNode(root, 0.0, 0.0, 1.0, 1.0, p);
        return champion;    
    }

    private void checkingNode(Node nd, double xl, double yl, double xu, double yu, Point2D p) {
        if (nd == null) return;
        //StdOut.println(champion.toString());
        double dist = p.distanceSquaredTo(champion);
        Point2D current = new Point2D(nd.x, nd.y);
        double currentdist = p.distanceSquaredTo(current);
        if (currentdist < dist) {
            champion = new Point2D(nd.x, nd.y);
            dist = currentdist;
        }
        if (nd.level) {
            RectHV pointRect1 = new RectHV(xl, yl, Math.min(nd.x, xu), yu);
            if (dist > pointRect1.distanceSquaredTo(p))
                checkingNode(nd.left, xl, yl, Math.min(nd.x, xu), yu, p);
            RectHV pointRect2 = new RectHV(Math.max(xl, nd.x), yl, xu, yu);
            if (dist > pointRect2.distanceSquaredTo(p))
                checkingNode(nd.right, Math.max(xl, nd.x), yl, xu, yu, p);
        }
        else {
            RectHV pointRect3 = new RectHV(xl, yl, xu, Math.min(nd.y, yu));
            if (dist > pointRect3.distanceSquaredTo(p))
                checkingNode(nd.left, xl, yl, xu, Math.min(nd.y, yu), p);
            RectHV pointRect4 = new RectHV(xl, Math.max(yl, nd.y), xu, yu);
            if (dist > pointRect4.distanceSquaredTo(p))
                checkingNode(nd.right, xl, Math.max(yl, nd.y), xu, yu, p);
        }
    }
    
    // unit testing of the methods (optional) 
    public static void main(String[] args)   {
        KdTree tree = new KdTree();
        Point2D p1 = new Point2D(1.0, 0.0);
        Point2D p2 = new Point2D(0.0, 0.0);
        Point2D p3 = new Point2D(0.0, 1.0);
        Point2D p = new Point2D(0.5, 0.5);

        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        StdOut.println("Size = " + tree.size());
        StdOut.println("Contains (0.5, 0.5)? " + tree.contains(p));
        StdOut.println("Contains (0.5, 0.5)? " + tree.contains(p));
        tree.insert(p);
        StdOut.println("Contains (0.5, 0.5)? " + tree.contains(p));
        StdOut.println("Nearest (0.0, 1.0)? " + tree.nearest(p3));
        
    }              
}
