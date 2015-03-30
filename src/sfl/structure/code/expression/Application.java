package sfl.structure.code.expression;

import sfl.translator.TranslationException;

import java.util.List;
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
    public void getConstraints(List<String> constraints, Map<Identifier, String> codes, String myCode) throws TranslationException {
        int depth = 0;
        Expression left = function;
        while (!(left instanceof TypeConstructor)) {
            if (left instanceof Application) {
                left = ((Application) left).function;
                depth++;
            } else {
                throw new TranslationException("Incorrect function argument: " + this);
            }
        }
        argument.getConstraints(constraints, codes, "((" + left + ") " + myCode + ").args.get(" + depth + ")");
        function.getConstraints(constraints, codes, myCode);
    }

    @Override
    public String toString() {
        return "(" + function + " " + argument + ")";
    }
}
