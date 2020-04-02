import exceptions.InvalidTypeException;
import exceptions.SyntaxException;
import expressions.Expression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parsers.ExpressionParser;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionParserTest {
    private ExpressionParser parser;

    @BeforeEach
    void init(){
        parser = new ExpressionParser();
    }

    @Test
    void test1(){
        String input = "((element+10)+90)";
        Expression expression = parser.parse(input);

        assertEquals(input, expression.toString());
    }

    @Test
    void test2(){
        String input = "(((element+5)*(10-23))+(element-7))";
        Expression expression = parser.parse(input);

        assertEquals(input, expression.toString());
    }

    @Test
    void test3(){
        String input = "((element+5)*((10-23)+(element-7)))";
        Expression expression = parser.parse(input);

        assertEquals(input, expression.toString());
    }

    @Test
    void testTypeException1(){
        String input = "((element+5)*((10-23)&(element-7)))";

        InvalidTypeException thrown = assertThrows(InvalidTypeException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("unable to apply logic operation on arithmetic type expression"));
    }

    @Test
    void testTypeException2(){
        String input = "((element>5)*(element<45))";

        InvalidTypeException thrown = assertThrows(InvalidTypeException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("unable to apply arithmetic operation on boolean type expression"));
    }

    @Test
    void testTypeException3(){
        String input = "((element>5)=(element<45))";

        InvalidTypeException thrown = assertThrows(InvalidTypeException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("unable to apply comparison operation on boolean type expression"));
    }

    @Test
    void testSyntaxException(){
        String input = "(element>)";

        SyntaxException thrown = assertThrows(SyntaxException.class, () -> parser.parse(input));

        assertTrue(thrown.getMessage().contains("error in expression syntax"));
    }
}
