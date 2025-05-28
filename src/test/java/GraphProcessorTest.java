package test.java;

import static org.junit.jupiter.api.Assertions.*;

import main.java.GraphProcessor;
import main.java.utils.FileUtils;
import main.java.utils.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphProcessorTest {

    private GraphProcessor graphProcessor;

    @BeforeEach
    public void setUp() {
        graphProcessor = new GraphProcessor();
        // 初始化图结构
        String text = FileUtils.readFile("./resources/Easy Test.txt");
        text = String.join(" ", StringUtils.preprocess(text)); // 使用 String.join 将数组转换为字符串
        graphProcessor.generateGraph(text.split(" "));

    }

    // queryBridgeWords 黑盒测试
    @Test
    public void testNoBridgeWords() {
        String result = graphProcessor.queryBridgeWords("but", "the");
        assertEquals("No bridge words from but to the!", result);
    }

    @Test
    public void testExistingBridgeWords() {
        String result = graphProcessor.queryBridgeWords("but", "data");
        assertEquals("The bridge words from but to data are: the", result);
    }

    @Test
    public void testWord1NotFound() {
        String result = graphProcessor.queryBridgeWords("butthe", "data");
        assertEquals("No word1 or word2 in the graph!", result);
    }

    @Test
    public void testWord2NotFound() {
        String result = graphProcessor.queryBridgeWords("but", "butthe");
        assertEquals("No word1 or word2 in the graph!", result);
    }


    // calcShortestPath 白盒测试
    @Test
    public void testNonexistentNodes() {
        // 测试用例 1
        String result = graphProcessor.calcShortestPath("but", "butthe");
        assertEquals("No word1 or word2 in the graph!", result);
    }

    @Test
    public void testNoPathFound() {
        // 测试用例 2
        String result = graphProcessor.calcShortestPath("again", "it");
        assertEquals("again to it is unreachable.", result);
    }

    @Test
    public void testPathFound() {
        // 测试用例 3
        String result = graphProcessor.calcShortestPath("but", "so");
        assertEquals("Shortest path from but to so: but -> the -> data -> so (Length: 3.0)", result);
    }
}