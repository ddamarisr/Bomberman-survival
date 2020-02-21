package businessLogic;

import data.Board;
import data.Coordinate;
import data.Enemy;
import data.Tiles;
import java.util.ArrayList;
import java.util.List;
public class AStarTest {

    
    public static int[][] convertIntegers(List<Integer> integers)
{
    int[][] ret = new int[integers.size()][integers.size()];
    return ret;
}
   /* 
    public static void main(String[] args) {
   
        Node initialNode = new Node(5, 3);
        Node finalNode = new Node(2, 5);
        int rows = 31;
        int cols = 13;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
       // int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        ArrayList<Coordinate> board = Game.getListaRompible();
        System.out.println(board);
        System.out.println(board);
        //int x=board.get(0).getxPos();
        //int fila=board.get(0).getyPos();
        int [][]board2= Enemy.convertIntegers(board);
        
        
        
        aStar.setBlocks(board2);
        List<Node> path = aStar.findPath();
        System.out.println("IMprime");
        for (Node node : path) {
            System.out.println(node);
    
      //     }

        //Search Area
        //      0   1   2   3   4   5   6
        // 0    -   -   -   -   -   -   -
        // 1    -   -   -   B   -   -   -
        // 2    -   I   -   B   -   F   -
        // 3    -   -   -   B   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -

        //Expected output with diagonals
        //Node [row=2, col=1]
        //Node [row=1, col=2]
        //Node [row=0, col=3]
        //Node [row=1, col=4]
        //Node [row=2, col=5]

        //Search Path with diagonals
        //      0   1   2   3   4   5   6
        // 0    -   -   -   *   -   -   -
        // 1    -   -   *   B   *   -   -
        // 2    -   I*  -   B   -  *F   -
        // 3    -   -   -   B   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -

        //Expected output without diagonals
        //Node [row=2, col=1]
        //Node [row=2, col=2]
        //Node [row=1, col=2]
        //Node [row=0, col=2]
        //Node [row=0, col=3]
        //Node [row=0, col=4]
        //Node [row=1, col=4]
        //Node [row=2, col=4]
        //Node [row=2, col=5]

        //Search Path without diagonals
        //      0   1   2   3   4   5   6
        // 0    -   -   *   *   *   -   -
        // 1    -   -   *   B   *   -   -
        // 2    -   I*  *   B   *  *F   -
        // 3    -   -   -   B   -   -   -
        // 4    -   -   -   -   -   -   -
        // 5    -   -   -   -   -   -   -
    }
    }
*/
}