package expressions;

/**
 * Expression which represent unknown variable
 * called only 'element' in current language
 *
 * @author Arina Fedorovskaya
 */

public class Element extends SingleExpression {

    /**
     * Constructor a {@code Element}
     */
    public Element(){
        this.type = ExpressionType.ELEMENT;
    }

    @Override
    public String toString() {
        return "element";
    }
}
