import java.util.ArrayList;

public class CallParser {
    ExpressionParser expressionParser = new ExpressionParser();

    public ArrayList<Node> parse(String input){
        if(!checkCallCorrectness(input)){
            return null; //TODO exceptions
        }

        ArrayList<Node> nodes = new ArrayList<>();

        for (String s : input.split("%>%")) {
            String[] call = s.split("[{}]");
            nodes.add(new Node(expressionParser.parse(call[1]), call[0]));
        }

        return nodes;
    }

    private boolean checkCallCorrectness(String input){
        return input.matches("^(map|filter)\\{[a-z0-9\\-+*><&|=()]+}(%>%(map|filter)\\{[a-z0-9\\-+*><&|=()]+})*$");
    }

//    private boolean checkTypes(){ //TODO check types
//
//    }

    static class Node{
        private Expression expression;
        private String type;

        Node(Expression expression, String type){
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
