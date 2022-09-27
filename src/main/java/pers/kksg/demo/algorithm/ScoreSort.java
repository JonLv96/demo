package pers.kksg.demo.algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lvqiang
 * @Date: 2022/09/27/22:34
 * @Description:
 *
 * 给定一些同学的信息（名字，成绩）序列，请你将他们的信息按照成绩从高到低或从低到高的排列,相同成绩
 *
 * 都按先录入排列在前的规则处理。
 *
 * 例示：
 * jack      70
 * peter     96
 * Tom       70
 * smith     67
 *
 * 从高到低  成绩
 * peter     96
 * jack      70
 * Tom       70
 * smith     67
 *
 * 从低到高
 *
 * smith     67
 *
 * jack      70
 *
 * Tom       70
 * peter     96
 *
 * 注：0代表从高到低，1代表从低到高
 *
 * 数据范围：人数：1\le n \le 200\1≤n≤200
 * 进阶：时间复杂度：O(nlogn)\O(nlogn) ，空间复杂度：O(n)\O(n)
 * 输入描述：
 * 第一行输入要排序的人的个数n，第二行输入一个整数表示排序的方式，之后n行分别输入他们的名字和成绩，以一个空格隔开
 *
 * 输出描述：
 * 按照指定方式输出名字和成绩，名字和成绩之间以一个空格隔开
 *
 *
 * 输入：
 * 3
 * 0
 * fang 90
 * yang 50
 * ning 70
 * 输出：
 * fang 90
 * ning 70
 * yang 50
 */
public class ScoreSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<Integer, String> map = new HashMap<>();
        while (scanner.hasNext()) {
            int num = Integer.parseInt(scanner.nextLine());
            // 0-逆序， 1-顺序
            int sort = Integer.parseInt(scanner.nextLine());
            int[][] indexWithScore = new int[num][2];
            for (int i = 0; i < num; i++) {
                String[] nameWithScore = scanner.nextLine().split("\\s+");
                indexWithScore[i][0] = i;
                indexWithScore[i][1] = Integer.parseInt(nameWithScore[1]);
                map.put(i, nameWithScore[0]);
            }
            Arrays.sort(indexWithScore, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    if (sort == 1) {
                        return o1[1] - o2[1];
                    } else {
                        return o2[1] - o1[1];
                    }
                }
            });

            for (int i = 0; i < num; i++) {
                System.out.println(map.get(indexWithScore[i][0]) + " " + indexWithScore[i][1]);
            }

        }


    }

}
