import java.util.Stack;
import java.util.LinkedList;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
/*
 *  Author: Melnikov Valeri (MelnikoffVA@yandex.ru)
 *  Date: 19.02.2016
 *  This class is used to apply the A* search algorithm implemented
 *  by using priority queues to the 8-puzzle problem.
 */
public class Solver {
    private final LinkedList<Board> seq2;
    private final boolean solvable;
    
    private final class SearchNode implements Comparable<SearchNode>
    {
        private final Board board;
        private int moves;
        private final SearchNode prev;
        private int prior = -1;
        
        public SearchNode(Board boardt, int movest, SearchNode prevt)
        {
            this.board = boardt;
            this.moves = movest;
            this.prev = prevt;
        }
        
        private int priority()
        {
            if (prior == -1)
            {
                prior = moves + board.manhattan();
                return prior;
            }
            else
                return prior;
        }
        
        public int compareTo(SearchNode that)
        {
            if (this.priority() < that.priority())
                return -1;
            else if (this.priority() > that.priority())
                return +1;
            return 0;
        }
    }
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new java.lang.NullPointerException();
        
        Board board = initial;
        Board board2 = board.twin();
        
        MinPQ<SearchNode> minpq = new MinPQ<SearchNode>();
        SearchNode node = new SearchNode(board, 0, null);
        
        MinPQ<SearchNode> minpq2 = new MinPQ<SearchNode>();
        SearchNode node2 = new SearchNode(board2, 0, null);
        
        seq2 = new LinkedList<Board>();
        
        while(true)
        {
            if(board.isGoal())
            {
                solvable = true;
                seq2.addFirst(node.board);
                while (node.prev != null)
                {
                    node = node.prev;
                    seq2.addFirst(node.board);
                }
                return;
            }
            if(board2.isGoal())
            {
                solvable = false;
                return;
            }
            
            Iterable<Board> neighbor = board.neighbors();
            for (Board a : neighbor)
            {
                if (node.prev == null || !a.equals(node.prev.board))
                    minpq.insert(new SearchNode(a, node.moves + 1, node));
            }
            node = minpq.delMin();
            board = node.board;
            
            Iterable<Board> neighbor2 = board2.neighbors();
            for (Board a : neighbor2)
            {
                if (node2.prev == null || !a.equals(node2.prev.board))
                    minpq2.insert(new SearchNode(a, node2.moves + 1, node2));
            }
            node2 = minpq2.delMin();
            board2 = node2.board;
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (!solvable)
            return -1;
        return seq2.size() - 1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if(!solvable)
            return null;
        return seq2;
    }
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        Solver solver = new Solver(initial);
        
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}