package expressions;

public class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private char operation;

    public BinaryExpression(Expression left, Expression right, char operation, ExpressionType type) {
        this.left = left;
        this.right = right;
        this.operation = operation;
        this.type = type;
    }

    public void setElement(Expression expression) {
        if(left instanceof Element){
            left = expression;
        }else if(left instanceof BinaryExpression){
            ((BinaryExpression)left).setElement(expression);
        }

        if(right instanceof Element){
            right = expression;
        }else if(right instanceof BinaryExpression){
            ((BinaryExpression)right).setElement(expression);
        }
    }

    @Override
    public String toString() {
        return "(" + left.toString() + operation + right.toString() + ")";
    }
}
