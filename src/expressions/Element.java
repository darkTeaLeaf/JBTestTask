package expressions;

public class Element extends SingleExpression {

    public Element(){
        this.type = ExpressionType.ELEMENT;
    }

    @Override
    public String toString() {
        return "element";
    }
}
