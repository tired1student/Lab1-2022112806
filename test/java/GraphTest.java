package main.java.test;

import main.java.Graph;
import main.java.GraphProcessor;
import main.java.Main;
import main.java.utils.FileUtils;
import main.java.utils.StringUtils;

import java.util.Scanner;

public class GraphTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GraphProcessor graphProcessor = new GraphProcessor();

        System.out.println("请输入文本文件路径：");
        String filePath = scanner.nextLine();
        String text = FileUtils.readFile(filePath);
        text = StringUtils.preprocess(text);

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
                    System.out.println("请输入两个单词：");
                    word1 = scanner.nextLine();
                    word2 = scanner.nextLine();
                    System.out.println(graphProcessor.calcShortestPath(word1, word2));
                    break;
                case 5:
                    System.out.println("请输入单词：");
                    String word = scanner.nextLine();
                    System.out.println(graphProcessor.calPageRank(word));
                    break;
                case 6:
                    System.out.println(graphProcessor.randomWalk());
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