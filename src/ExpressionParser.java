public class ExpressionParser {
    private String input;
    private int i;

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
            left = new BinaryExpression(left, parseRelation(), tk);
            tk = get();
        }

        return left;
    }

    private Expression parseRelation() {
        Expression left = parseTerm();

        char tk = get();
        while (tk == '>' || tk == '<' || tk == '=') {
            increaseI();
            left = new BinaryExpression(left, parseTerm(), tk);
            tk = get();
        }

        return left;
    }

    private Expression parseTerm() {
        Expression left = parsePrimary();

        char tk = get();
        while (tk == '+' || tk == '-' || tk == '*') {
            increaseI();
            left = new BinaryExpression(left, parsePrimary(), tk);
            tk = get();
        }

        return left;
    }

    private Expression parsePrimary() {
        Expression expression;

        if (get() == '(') {
            increaseI();
            expression = parseLogic();
            increaseI();
        } else {
            expression = parseConstantOrElement(); //new primary?
        }

        return expression;
    }

    private SingleExpression parseConstantOrElement() {
        StringBuilder num = new StringBuilder();
        char c = get();
        if (Character.isDigit(c)) {
            while (Character.isDigit(c)) {
                num.append(c);
                increaseI();
                c = get();
            }

            int res = Integer.parseInt(num.toString());

            return new ConstantExpression(res);
        } else if (Character.isAlphabetic(c)) {
            while (Character.isAlphabetic(c)) {
                num.append(c);
                increaseI();
                c = get();
            }

            if (num.toString().equals("element")) {
                return new Element();
            }
        }
        return null; //TODO exceptions
    }

    private char get() {
        char cur;
        while (true) {
            if (i < input.length()) {
                cur = input.charAt(i); //TODO delete handling spaces
                if (cur == ' ') {
                    increaseI();
                } else {
                    return input.charAt(i);
                }
            } else {
                return ' ';
            }
        }
    }

    private void increaseI() {
        if (i < input.length()) {
            i++;
        }
    }

}
