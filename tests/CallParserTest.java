import exceptions.InvalidTypeException;
import exceptions.SyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.CallParser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CallParserTest {
    private CallParser parser;

    @BeforeEach
    void init(){
        parser = new CallParser();
    }

    @Test
    void test1(){
        ArrayList<CallParser.Node> nodes = parser.parse("filter{(element>10)}%>%filter{(element<20)}");

        assertEquals("[filter{(element>10)}, filter{(element<20)}]", nodes.toString());
    }

    @Test
    void test2(){
        ArrayList<CallParser.Node> nodes =
                parser.parse("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}");

        assertEquals("[map{(element+10)}, filter{(element>10)}, map{(element*element)}]", nodes.toString());
    }

    @Test
    void test3(){
        ArrayList<CallParser.Node> nodes =
                parser.parse("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}");

        assertEquals("[filter{(element>0)}, filter{(element<0)}, map{(element*element)}]", nodes.toString());
    }

    @Test
    void testTypeException1(){
        String input = "filter{(element-10)}%>%filter{(element<0)}%>%map{(element*element)}";

        InvalidTypeException thrown = assertThrows(InvalidTypeException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("type error in the following part of code 'filter{(element-10)}'"));
    }

    @Test
    void testTypeException2(){
        String input = "filter{(element>0)}%>%filter{(element<0)}%>%map{(element>(element-10))}";

        InvalidTypeException thrown = assertThrows(InvalidTypeException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("type error in the following part of code 'map{(element>(element-10))}'"));
    }

    @Test
    void testSyntaxException1(){
        String input = "map{(element+10)}filter{(element>10)}%>%map{(element*element)}";

        SyntaxException thrown = assertThrows(SyntaxException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("error in call syntax or invalid symbols"));
    }

    @Test
    void testSyntaxException2(){
        String input = "map{(element~10)}%>%filter{(element>10)}%>%map{(element*element)}";

        SyntaxException thrown = assertThrows(SyntaxException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("error in call syntax or invalid symbols"));
    }

    @Test
    void testSyntaxException3(){
        String input = "map{(element-10)}%>%filt{(element>10)}%>%map{(element*element)}";

        SyntaxException thrown = assertThrows(SyntaxException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("error in call syntax or invalid symbols"));
    }
}
