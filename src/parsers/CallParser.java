package parsers;

import exceptions.InvalidTypeException;
import exceptions.SyntaxException;
import expressions.*;

import java.util.ArrayList;

/**
 * Parsing of call chain
 *
 * @author Arina Fedorovskaya
 */

public class CallParser {
    private final ExpressionParser expressionParser = new ExpressionParser();

    /**
     * Parsing of input string according call chain syntax
     *
     * @param input string to parse
     * @return {@code ArrayList} consists of calls represented as instances of {@code Node}
     */
    public ArrayList<Node> parse(String input) {
        if (!checkCallCorrectness(input)) {
            throw new SyntaxException("error in call syntax or invalid symbols");
        }

        ArrayList<Node> nodes = new ArrayList<>();

        for (String s : input.split("%>%")) {
            String[] call = s.split("[{}]");
            Expression expression = expressionParser.parse(call[1]);

            if (checkTypes(expression, call[0])) {
                if (call[0].equals("map")) {
                    nodes.add(new Node(expression, CallType.MAP));
                } else if (call[0].equals("filter")) {
                    nodes.add(new Node(expression, CallType.FILTER));
                } else {
                    throw new SyntaxException("no such type of call");
                }
            } else {
                throw new InvalidTypeException("type error in the following part of code '" + s + "'");
            }
        }

        return nodes;
    }

    /**
     * Check correctness of call chain syntax in input string
     *
     * @param input string to check
     * @return true if syntax is correct
     */
    private boolean checkCallCorrectness(String input) {
        return input.matches("^(map|filter)\\{((element)|[0-9\\-+*><&|=()])+}(%>%(map|filter)\\{((element)|[0-9\\-+*><&|=()])+})*$");
    }

    /**
     * Check correctness of return value types of expressions in call
     *
     * @param expression expression in call
     * @param callType type of call
     * @return true if filter call and return value types of expression is boolean or single element
     *              if map call and return value types of expression is arithmetic or single element
     */
    private boolean checkTypes(Expression expression, String callType) {
        return (callType.equals("filter") && (expression.getType().equals(ExpressionType.BOOLEAN) ||
                expression.getType().equals(ExpressionType.ELEMENT))) ||
                (callType.equals("map") && (expression.getType().equals(ExpressionType.ARITHMETIC) ||
                expression.getType().equals(ExpressionType.ELEMENT)));
    }

    /**
     * Class to store call information: expression
     * in call and type of call (filter or map)
     */
    public static class Node {
        private Expression expression;
        private CallType type;

        /**
         * Constructor a {@code Node}
         *
         * @param expression expression in call
         * @param type type of call
         */
        public Node(Expression expression, CallType type) {
            this.expression = expression;
            this.type = type;
        }

        public Expression getExpression() {
            return expression;
        }

        public CallType getType() {
            return type;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }

        /**
         * Replace all instances of class {@code Element} in current expression
         *
         * @param expression value to which instances of class {@code Element} should be replaced
         */
        public void setElement(Expression expression){
            if(this.expression instanceof BinaryExpression){
                ((BinaryExpression) this.expression).setElement(expression);
            } else if(this.expression instanceof Element){
                this.expression = expression;
            }
        }

        @Override
        public String toString() {
            return type.name().toLowerCase() + "{" + expression + "}";
        }
    }
}
