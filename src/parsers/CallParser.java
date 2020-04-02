package parsers;

import exceptions.InvalidTypeException;
import exceptions.SyntaxException;
import expressions.Expression;

import java.util.ArrayList;

public class CallParser {
    ExpressionParser expressionParser = new ExpressionParser();

    public ArrayList<Node> parse(String input) {
        if(!checkCallCorrectness(input)){
            throw new SyntaxException("error in call syntax or invalid symbols");
        }

        ArrayList<Node> nodes = new ArrayList<>();

        for (String s : input.split("%>%")) {
            if(checkTypes(s)) {
                String[] call = s.split("[{}]");
                nodes.add(new Node(expressionParser.parse(call[1]), call[0]));
            }else {
                throw new InvalidTypeException("type error in the following part of code '" + s + "'");
            }
        }

        return nodes;
    }

    private boolean checkCallCorrectness(String input){
        return input.matches("^(map|filter)\\{((element)|[0-9\\-+*><&|=()])+}(%>%(map|filter)\\{((element)|[0-9\\-+*><&|=()])+})*$");
    }

    private boolean checkTypes(String input){
        return input.matches("^((map\\{[a-z0-9\\-+*()]+})|filter\\{[a-z0-9&|><=()]+})");
    }

    public static class Node{
        private Expression expression;
        private String type;

        public Node(Expression expression, String type){
            this.expression = expression;
            this.type = type;
        }

        public Expression getExpression() {
            return expression;
        }

        public String getType() {
            return type;
        }

        public void setExpression(Expression expression) {
            this.expression = expression;
        }

        @Override
        public String toString() {
            return type + "{" + expression + "}";
        }
    }
}
