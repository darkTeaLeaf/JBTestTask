package parsers;

import exceptions.InvalidTypeException;
import exceptions.SyntaxException;
import expressions.*;

/**
 * Parsing of expression
 *
 * @author Arina Fedorovskaya
 */

public class ExpressionParser {
    private String input;
    private int i;  /* position of current token in string*/

    /**
     * Parsing of input string according expression rules
     *
     * @param input string to parse
     * @return {@code Expression} tree of parsed expression elements
     */
    public Expression parse(String input) {
        this.input = input;
        this.i = 0;

        return parseLogic();
    }

    private Expression parseLogic() {
        Expression left = parseRelation();

        char tk = get();
        while (tk == '&' || tk == '|') {
            increaseI();
            Expression right = parseRelation();

            if (left.getType().equals(ExpressionType.BOOLEAN) && right.getType().equals(ExpressionType.BOOLEAN)) {
                left = new BinaryExpression(left, right, tk, ExpressionType.BOOLEAN);
                tk = get();
            } else {
                throw new InvalidTypeException("unable to apply logic operation on arithmetic type expression");
            }
        }

        return left;
    }

    private Expression parseRelation() {
        Expression left = parseTerm();

        char tk = get();
        while (tk == '>' || tk == '<' || tk == '=') {
            increaseI();
            Expression right = parseRelation();

            if ((left.getType().equals(ExpressionType.ARITHMETIC) || left.getType().equals(ExpressionType.ELEMENT)) ||
                (right.getType().equals(ExpressionType.ARITHMETIC) || right.getType().equals(ExpressionType.ELEMENT))) {
                left = new BinaryExpression(left, right, tk, ExpressionType.BOOLEAN);
                tk = get();
            } else {
                throw new InvalidTypeException("unable to apply comparison operation on boolean type expression");
            }
        }

        return left;
    }

    private Expression parseTerm() {
        Expression left = parsePrimary();

        char tk = get();
        while (tk == '+' || tk == '-' || tk == '*') {
            increaseI();
            Expression right = parsePrimary();

            if ((left.getType().equals(ExpressionType.ARITHMETIC) || left.getType().equals(ExpressionType.ELEMENT)) &&
                (right.getType().equals(ExpressionType.ARITHMETIC)) || right.getType().equals(ExpressionType.ELEMENT)) {
                left = new BinaryExpression(left, right, tk, ExpressionType.ARITHMETIC);
                tk = get();
            } else {
                throw new InvalidTypeException("unable to apply arithmetic operation on boolean type expression");
            }
        }

        return left;
    }

    private Expression parsePrimary() {
        Expression expression;

        if (get() == '(') {
            increaseI();
            expression = parseLogic();
            if (get() == ')' && expression instanceof BinaryExpression) {
                increaseI();
            } else {
                throw new SyntaxException("error in expression syntax");
            }
        } else {
            expression = parseConstantOrElement(); //new primary?
        }

        return expression;
    }

    private SingleExpression parseConstantOrElement() {
        SingleExpression expression = null;
        StringBuilder num = new StringBuilder();

        char c = get();
        if (Character.isDigit(c)) {
            while (Character.isDigit(c)) {
                num.append(c);
                increaseI();
                c = get();
            }

            int res = Integer.parseInt(num.toString());

            expression = new ConstantExpression(res);
        } else if (Character.isAlphabetic(c)) {
            while (Character.isAlphabetic(c)) {
                num.append(c);
                increaseI();
                c = get();
            }

            if (num.toString().equals("element")) {
                expression = new Element();
            }
        } else {
            throw new SyntaxException("error in expression syntax");
        }

        return expression;
    }

    private char get() {
        if (i < input.length()) {
            return input.charAt(i);
        } else {
            return ' ';
        }
    }

    private void increaseI() {
        if (i < input.length()) {
            i++;
        }
    }

}
