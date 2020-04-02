package parsers;

import exceptions.InvalidTypeException;
import exceptions.SyntaxException;
import expressions.*;

import java.util.ArrayList;

public class CallParser {
    private final ExpressionParser expressionParser = new ExpressionParser();

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

    private boolean checkCallCorrectness(String input) {
        return input.matches("^(map|filter)\\{((element)|[0-9\\-+*><&|=()])+}(%>%(map|filter)\\{((element)|[0-9\\-+*><&|=()])+})*$");
    }

    private boolean checkTypes(Expression expression, String callType) {
        return (callType.equals("filter") && (expression.getType().equals(ExpressionType.BOOLEAN) ||
                expression.getType().equals(ExpressionType.ELEMENT))) ||
                (callType.equals("map") && (expression.getType().equals(ExpressionType.ARITHMETIC) ||
                expression.getType().equals(ExpressionType.ELEMENT)));
    }

    public static class Node {
        private Expression expression;
        private CallType type;

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
