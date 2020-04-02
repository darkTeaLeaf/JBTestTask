package expressions;

/**
 * Expression which consist of two expressions with
 * operation between
 *
 * @author Arina Fedorovskaya
 */

public class BinaryExpression extends Expression {
    private Expression left;
    private Expression right;
    private char operation;

    /**
     * Constructor a {@code BinaryExpression}
     *
     * @param left one expression
     * @param right second expression
     * @param operation operation between expressions
     * @param type type of current expression return value
     */

    public BinaryExpression(Expression left, Expression right, char operation, ExpressionType type) {
        this.left = left;
        this.right = right;
        this.operation = operation;
        this.type = type;
    }

    /**
     * Replace all instances of class {@code Element} in current expression
     *
     * @param expression value to which instances of class {@code Element} should be replaced
     */

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
