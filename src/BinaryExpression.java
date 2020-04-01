public class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private char operation;

    BinaryExpression(Expression left, Expression right, char operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public String toString() {
        return left.toString() + operation + right.toString();
    }

    @Override
    public void setElement(Expression expression) {
        if(left instanceof Element){
            left = expression;
        }else {
            left.setElement(expression);
        }

        if(right instanceof Element){
            right = expression;
        }else {
            right.setElement(expression);
        }
    }
}
