package test.java;

import static org.junit.jupiter.api.Assertions.*;

import main.java.GraphProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class GraphProcessorTest {

    private GraphProcessor graphProcessor;

    @BeforeEach
    public void setUp() {
        graphProcessor = new GraphProcessor();
        // 在这里初始化图结构，根据实际需求添加单词和边
        String[] words = {"but", "the", "data", "example"};
        graphProcessor.generateGraph(words);
        graphProcessor.generateGraph(new String[]{"the", "data"});
    }

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


}