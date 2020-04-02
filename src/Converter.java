import expressions.BinaryExpression;
import expressions.Element;
import expressions.ExpressionType;
import parsers.CallParser;
import parsers.CallType;

/**
 * Converting of call chain to equivalent expression of
 * the form filter{expression}%>%map{expression}
 *
 * @author Arina Fedorovskaya
 */

public class Converter {
    private final CallParser parser = new CallParser();

    /**
     *  Converting of call chain to expression
     *
     * @param input string to convert
     * @return {@code String} with equivalent expression of the form filter{expression}%>%map{expression}
     */
    public String convert(String input) {
        CallParser.Node map = new CallParser.Node(new Element(), CallType.MAP);
        CallParser.Node filter = new CallParser.Node(new Element(), CallType.FILTER);

        for (CallParser.Node node : parser.parse(input)) {
            node.setElement(map.getExpression());

            if (node.getType().equals(CallType.MAP)) {
                map.setExpression(node.getExpression());
            }

            if (node.getType().equals(CallType.FILTER)) {
                if (filter.getExpression() instanceof Element) {
                    filter.setExpression(node.getExpression());
                } else {
                    filter.setExpression(new BinaryExpression(filter.getExpression(), node.getExpression(),
                            '&', ExpressionType.BOOLEAN));
                }
            }
        }

        return filter.toString() + "%>%" + map.toString();
    }
}
