package expressions;

/**
 * Abstract instance of expressions
 * Contains only {@code ExpressionType} variable which
 * represent type of current expression return value
 *
 * @author Arina Fedorovskaya
 */

abstract public class Expression {
    protected ExpressionType type;

    public ExpressionType getType() {
        return type;
    }
}
