package sfl.structure;

import sfl.structure.code.expression.Expression;
import sfl.structure.code.expression.Identifier;

import java.util.List;

public class Definition implements Statement {
    private Identifier name;
    private List<Expression> arguments;
    private Expression value;

    public Definition(Identifier name, List<Expression> arguments, Expression value) {
        this.name = name;
        this.arguments = arguments;
        this.value = value;
    }

    public Identifier getName() {
        return name;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public String toString() {
        String result = name.toString();
        for (Expression expression : arguments) {
            result += " " + expression;
        }
        result += " = " + value;
        return result;
    }
}
