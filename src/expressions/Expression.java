package expressions;

abstract public class Expression {
    protected ExpressionType type;

    public abstract void setElement(Expression expression);

    public ExpressionType getType() {
        return type;
    }
}
