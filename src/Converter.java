import expressions.BinaryExpression;
import expressions.Element;
import parsers.CallParser;

public class Converter {
    private CallParser parser = new CallParser();

    public String convert(String input) {
        CallParser.Node map = new CallParser.Node(new Element(), "map");
        CallParser.Node filter = new CallParser.Node(new Element(), "filter");

        for (CallParser.Node node : parser.parse(input)) {
            node.getExpression().setElement(map.getExpression());

            if (node.getType().equals("map")) {
                map.setExpression(node.getExpression());
            }

            if (node.getType().equals("filter")) {
                if (filter.getExpression() instanceof Element) {
                    filter.setExpression(node.getExpression());
                } else {
                    filter.setExpression(new BinaryExpression(node.getExpression(), filter.getExpression(), '&'));
                }
            }
        }

        return filter.toString() + "%>%" + map.toString();
    }
}
