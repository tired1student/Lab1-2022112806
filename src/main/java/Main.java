package main.java;

import main.java.utils.FileUtils;
import main.java.utils.StringUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GraphProcessor graphProcessor = new GraphProcessor();

        // ./resources/Easy Test.txt
        // ./resources/Cursed Be The Treasure.txt
        System.out.println("请输入文本文件路径：");
        String filePath = scanner.nextLine();
        String text = FileUtils.readFile(filePath);
        text = String.join(" ", StringUtils.preprocess(text)); // 使用 String.join 将数组转换为字符串

        graphProcessor.generateGraph(text.split(" "));

        while (true) {
            System.out.println("\n请选择功能：");
            System.out.println("1. 展示有向图");
            System.out.println("2. 查询桥接词");
            System.out.println("3. 根据桥接词生成新文本");
            System.out.println("4. 计算最短路径");
            System.out.println("5. 计算 PageRank 值");
            System.out.println("6. 随机游走");
            System.out.println("7. 退出");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    graphProcessor.showDirectedGraph();
                    break;
                case 2:
                    System.out.println("请输入两个单词：");
                    String word1 = scanner.nextLine();
                    String word2 = scanner.nextLine();
                    System.out.println(graphProcessor.queryBridgeWords(word1, word2));
                    break;
                case 3:
                    System.out.println("请输入新文本：");
                    String inputText = scanner.nextLine();
                    System.out.println(graphProcessor.generateNewText(inputText));
                    break;
                case 4:
                    System.out.println("请输入一个或两个单词（用空格分隔）：");
                    String[] words = scanner.nextLine().split(" ");
                    if (words.length == 1) {
                        System.out.println(graphProcessor.calcAllShortestPathsFrom(words[0]));
                    } else if (words.length == 2) {
                        System.out.println(graphProcessor.calcShortestPath(words[0], words[1]));
                    } else {
                        System.out.println("输入无效！");
                    }
                    break;
                case 5:
                    System.out.println("请输入单词：");
                    String word = scanner.nextLine();
                    System.out.println(graphProcessor.calPageRank(word));
                    break;
                case 6:
                    String randomWalkResult = graphProcessor.randomWalk();
                    // System.out.println(randomWalkResult);
                    System.out.println("结果已保存到文件：random_walk_result.txt");
                    break;
                case 7:
                    System.out.println("退出程序。");
                    scanner.close();
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }
}


