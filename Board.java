import java.util.Stack;
/*
 *  Author: Melnikov Valeri (MelnikoffVA@yandex.ru)
 *  Date: 19.02.2016
 *  This class is used to model a board in the 8-puzzle problem.
 */
public class Board {
    
    private final int N;
    private final int[][] block;
                                           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        N = blocks.length;
        block = new int[N][N];
        
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                block[i][j] = blocks[i][j];
    }
    public int dimension()                 // board dimension N
    {
        return N;
    }
    public int hamming()                   // number of blocks out of place
    {
        int ham = 0;
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                   if ((i != N - 1 || j != N - 1) && block[i][j] != i*N + j + 1)
                       ++ham;
            }
        }
        return ham;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int sum = 0;
        int iexp, jexp;
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                if (block[i][j] == 0)
                    continue;
                iexp = (block[i][j] - 1) / N;
                jexp = (block[i][j] - 1) % N;
                if (iexp > i)
                    sum += iexp - i;
                else
                    sum += i - iexp;
                
                if (jexp > j)
                    sum += jexp - j;
                else
                    sum += j - jexp;
            }
        }
        return sum;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] bufmas = new int[N][N];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                bufmas[i][j] = block[i][j];
        
        if(bufmas[0][0] != 0)
        {
            int buf = bufmas[0][0];
            if (bufmas[0][1] != 0)
            {
                bufmas[0][0] = bufmas[0][1];
                bufmas[0][1] = buf;
            }
            else
            {
                bufmas[0][0] = bufmas[N - 1][N - 1];
                bufmas[N - 1][N - 1] = buf;
            }
        }
        else
        {
            int buf = bufmas[1][1];
            bufmas[1][1] = bufmas[1][0];
            bufmas[1][0] = buf;
        }
        return new Board(bufmas);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        
        if(this.N != that.N)
            return false;
        
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (this.block[i][j] != that.block[i][j])
                   return false;
        
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> stack = new Stack<Board>();
        int[][] bufmas = new int[N][N];
        int xblank = N - 1, yblank = N - 1;
        for (int i = 0; i < N; ++i)
        {
            for (int j = 0; j < N; ++j)
            {
                if (block[i][j] == 0)
                {
                    xblank = i;
                    yblank = j;
                }
                bufmas[i][j] = block[i][j];
            }
        }
        
        int dx[] = {1, 0, -1, 0};
        int dy[] = {0, 1, 0, -1};
        
        for (int i = 0; i < 4; ++i)
        {
            if (xblank + dx[i] < N && xblank + dx[i] >= 0
                    && yblank + dy[i] < N && yblank + dy[i] >= 0)
            {
                bufmas[xblank][yblank] = bufmas[xblank + dx[i]][yblank + dy[i]];
                bufmas[xblank + dx[i]][yblank + dy[i]] = 0;
                stack.push(new Board(bufmas));
                bufmas[xblank + dx[i]][yblank + dy[i]]
                    = block[xblank + dx[i]][yblank + dy[i]];
                bufmas[xblank][yblank] = block[xblank][yblank];
            }
        }
        return stack;
    }
    public String toString()               // string representation of this board
    {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", block[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}