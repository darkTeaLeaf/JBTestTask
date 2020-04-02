import expressions.BinaryExpression;
import expressions.Element;
import expressions.ExpressionType;
import parsers.CallParser;
import parsers.CallType;

public class Converter {
    private final CallParser parser = new CallParser();

    public String convert(String input) {
        CallParser.Node map = new CallParser.Node(new Element(), CallType.MAP);
        CallParser.Node filter = new CallParser.Node(new Element(), CallType.FILTER);

        for (CallParser.Node node : parser.parse(input)) {
            node.getExpression().setElement(map.getExpression());

            if (node.getType().equals(CallType.MAP)) {
                map.setExpression(node.getExpression());
            }

            if (node.getType().equals(CallType.FILTER)) {
                if (filter.getExpression() instanceof Element) {
                    filter.setExpression(node.getExpression());
                } else {
                    filter.setExpression(new BinaryExpression(node.getExpression(), filter.getExpression(),
                            '&', ExpressionType.BOOLEAN));
                }
            }
        }

        return filter.toString() + "%>%" + map.toString();
    }
}
