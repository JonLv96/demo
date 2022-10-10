package pers.kksg.demo.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/10/09/16:15
 * @Description: 腐烂的橘子
 * <p>
 * 在给定的m x n网格grid中，每个单元格可以有以下三个值之一：
 * <p>
 * 值0代表空单元格；
 * 值1代表新鲜橘子；
 * 值2代表腐烂的橘子。
 * 每分钟，腐烂的橘子周围4 个方向上相邻 的新鲜橘子都会腐烂。
 * <p>
 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回-1。
 */
public class RottenOranges {

    /**
     * 空格
     */
    public static final int SPACE = 0;
    /**
     * 新鲜
     */
    public static final int FRESH = 1;
    /**
     * 腐烂
     */
    public static final int ROTTEN = 2;


    public static void main(String[] args) {
        int[][] grid = {{2, 1, 1}, {1, 1, 0}, {0, 1, 1}};
        System.out.println(orangesRotting(grid));

    }

    public static int orangesRotting(int[][] grid) {
        int ROW_SIZE = grid.length;
        int COLUMN_SIZE = grid[0].length;
        //记录当前已腐烂的橘子
        Queue<int[]> rottens = new LinkedList<>();
        //记录新鲜橘子数
        int freshCount = 0;

        //将橘子分拣，记录已腐烂橘子
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ROTTEN) {
                    rottens.add(new int[]{i, j});
                }
                if (grid[i][j] == FRESH) {
                    freshCount++;
                }
            }
        }
        //记录扩散次数
        int rottenTime = 0;

        //当无新鲜橘子或者无可扩散腐烂区域则停止
         while (freshCount > 0 && !rottens.isEmpty()) {
            for (int i = rottens.size(); i > 0; i--) {
                int[] temp = rottens.poll();
                int row = temp[0];
                int column = temp[1];
                //判断是否可向上扩散
                if (row > 0 && grid[row - 1][column] == FRESH) {
                    //将新腐烂位置橘子设置腐烂状态
                    grid[row - 1][column] = ROTTEN;
                    //将新腐烂的橘子入队列
                    rottens.add(new int[]{row - 1, column});
                    freshCount--;
                }
                //判断是否可向下扩散
                if (row < ROW_SIZE - 1 && grid[row + 1][column] == FRESH) {
                    //将新腐烂位置橘子设置腐烂状态
                    grid[row + 1][column] = ROTTEN;
                    //将新腐烂的橘子入队列
                    rottens.add(new int[]{row + 1, column});
                    freshCount--;
                }
                //判断是否可向左扩散
                if (column > 0 && grid[row][column - 1] == FRESH) {
                    //将新腐烂位置橘子设置腐烂状态
                    grid[row][column - 1] = ROTTEN;
                    //将新腐烂的橘子入队列
                    rottens.add(new int[]{row, column - 1});
                    freshCount--;
                }
                //判断是否可向右扩散
                if (column < COLUMN_SIZE - 1 && grid[row][column + 1] == FRESH) {
                    //将新腐烂位置橘子设置腐烂状态
                    grid[row][column + 1] = ROTTEN;
                    //将新腐烂的橘子入队列
                    rottens.add(new int[]{row, column + 1});
                    freshCount--;
                }
            }
            rottenTime++;
        }

        if (freshCount > 0) {
            return -1;
        }
        return rottenTime;
    }
}
