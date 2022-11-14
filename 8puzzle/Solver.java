import edu.princeton.cs.algs4.MinPQ;
import java.lang.Math;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;
import java.util.Comparator; // Don't forget to add this


public class Solver {
    
    private class bNode{
        int mov = 0;
        Board board;
        int manhattan;
        bNode previous;
        } 
    
    private boolean solved = false;
    private boolean solvedtwin = false;
    
    private int m = 0;
    
    private MinPQ<bNode> pq;
    private MinPQ<bNode> pqtwin;
    
    private Stack<bNode> deletedNodes = new Stack<bNode>();
    private Stack<bNode> deletedNodestwin = new Stack<bNode>();
    
    //private Stack<Board> history = new Stack<Board>();
    //private Stack<Board> historytwin = new Stack<Board>();
     
    private Comparator<bNode> fscore(){ 

        return (b1, b2) -> Integer.compare(b1.manhattan+b1.mov, b2.manhattan+b2.mov);
        
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Null input.");
        
        pq = new MinPQ<bNode>(fscore());
        pqtwin = new MinPQ<bNode>(fscore());
        
        bNode ini = new bNode();
        ini.mov = 0;
        ini.board = initial;
        ini.manhattan = initial.manhattan();
        ini.previous = null;

        bNode initwin = new bNode();
        initwin.mov = 0;
        initwin.board = initial.twin(); // Previous bug: twin() changes the initial board by using "newboard = this" as initializer
        initwin.manhattan = initial.manhattan();
        initwin.previous = null;
        
        pq.insert(ini);
        pqtwin.insert(initwin);

        //StdOut.println("Starting with: ");
        //StdOut.println(ini.board);
        //StdOut.println(initwin.board);
       
        if (initial.isGoal()) {
            solved = true;
            solvedtwin = false;
            deletedNodes.push(ini);
            //history.push(initial);
        }
         else if (initwin.board.isGoal()) {
             solved = false;
             solvedtwin = true;
         }
        else {            
            while(!solved && !solvedtwin) {
                // StdOut.println("Original count: " + count);                
                if (pq.isEmpty()) break;
                
                bNode deleted = pq.delMin();
                
                deletedNodes.push(deleted);
                //history.push(deleted.board);

                //  StdOut.println("Deleted Original: ");
                //  StdOut.println(deleted.board);

                for (Board b : deleted.board.neighbors()) {
                    
                    bNode newNode = new bNode();
                    newNode.board = b;
                    newNode.manhattan = b.manhattan();
                    newNode.mov = deleted.mov + 1;

                    if (b.isGoal()) {                        
                        newNode.previous = deleted;
                        deletedNodes.push(newNode);
                        //history.push(b);
                        solved = true;
                        solvedtwin = false;
                        m = newNode.mov;
                        
                        break;
                    }
                    else {
                        // boolean inHistory = false;
                        // boolean inHistory = (history.search(b) > -1);
                        boolean sameasprevious;
                        if (deleted.previous == null) sameasprevious = false;
                        else sameasprevious =  b.equals(deleted.previous.board);
                        
                        
                        if (!sameasprevious) {
                            newNode.previous = deleted;
                            pq.insert(newNode);
                            // history.push(b);
                            //   StdOut.println("newNode: ");
                            //   StdOut.println(newNode.board);
                        }
                    }
                }
                if (pqtwin.isEmpty()) {
                    solvedtwin = false;
                }
                else {

                    //  StdOut.println("Twin count: " + counttwin);
                    bNode deletedtwin = pqtwin.delMin();
                    
                    deletedNodestwin.push(deletedtwin);
                    //historytwin.push(deletedtwin.board);

                    //  StdOut.println("Deletedtwin Twin: ");
                    //  StdOut.println(deletedtwin.board);
                    
                    for (Board b : deletedtwin.board.neighbors()) {
                        
                        bNode newNodetwin = new bNode();
                        newNodetwin.board = b;
                        newNodetwin.manhattan = deletedtwin.board.manhattan();
                        newNodetwin.mov = deletedtwin.mov + 1;
                     
                        if (b.isGoal()) {                        
                            
                            deletedNodestwin.push(newNodetwin);
                            //historytwin.push(b);
                            solvedtwin = true;
                            solved = false;
                            
                            break;
                        }
                        else {
                            
                            // boolean inHistoryTwin = false;
                            //boolean inHistoryTwin = (historytwin.search(b) > -1);
                            boolean sameasprevioustwin;
                            if (deletedtwin.previous == null) sameasprevioustwin = false;
                            else sameasprevioustwin =  b.equals(deletedtwin.previous.board);
                            
                            if (!sameasprevioustwin) {
                                newNodetwin.previous = deletedtwin;
                                pqtwin.insert(newNodetwin);
                                //historytwin.push(b);
                                
                                //   StdOut.println("newNode: ");
                                //   StdOut.println(newNode.board);
                            }
                        }
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solved;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        // if (solved) return m;
        // else return -1;
        if (!this.isSolvable()) return -1;
        else {
            int count = 0;
            for (Board b : this.solution())
                count++;
            return count-1;
        }        
    }
        
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solved) {
            Stack<Board> sol = new Stack<Board>();
            Stack<bNode> dNcopy = (Stack<bNode>)deletedNodes.clone();
            
            bNode current = dNcopy.peek();
            
            while(!dNcopy.empty()) {
                bNode b = dNcopy.pop();
                sol.push(b.board);
                if (!dNcopy.empty())
                    current = dNcopy.peek();
                else break;
                while (current != b.previous) {
                    dNcopy.pop();
                    current = dNcopy.peek();
                }
            }            
            Stack<Board> reverseSol = new Stack<Board>();
            while(!sol.empty()) {
                Board b = sol.pop();
                reverseSol.push(b);
            }
            
            return reverseSol;
        }
        else return null;
    }
    
    
        
    // test client (see below) 
    public static void main(String[] args)  {
        
        // create initial board from file
        int n = StdIn.readInt();
        
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            tiles[i][j] = StdIn.readInt();
        Board initial = new Board(tiles);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        StdOut.println("isSolvable? " + solver.isSolvable());

        StdOut.println("Number of moves: " + solver.moves());
        
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
           StdOut.println("Minimum number of moves = " + solver.moves());
           for (Board board : solver.solution())
               StdOut.println(board);
        }
    }
    
}
