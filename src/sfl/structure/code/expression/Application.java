package sfl.structure.code.expression;

import java.util.Map;

public class Application extends Expression {
    private Expression function, argument;

    public Application(Expression function, Expression argument) {
        this.function = function;
        this.argument = argument;
    }

    @Override
    public String generateCode(Map<Identifier, String> ids) {
        return "((Function) " + function.generateCode(ids) + ").apply(" + argument.generateCode(ids) + ")";
    }

    @Override
    public String toString() {
        return "(" + function + " " + argument + ")";
    }
}
