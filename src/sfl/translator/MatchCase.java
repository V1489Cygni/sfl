package sfl.translator;

import sfl.structure.code.expression.Expression;
import sfl.structure.code.expression.Identifier;
import sfl.structure.code.expression.Int;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchCase {
    private List<Expression> arguments;
    private Expression value;

    public MatchCase(List<Expression> arguments, Expression value) {
        this.arguments = arguments;
        this.value = value;
    }

    public int getArgsNumber() {
        return arguments.size();
    }

    private String getArgName(int i) {
        if (i == arguments.size() - 1) {
            return "o";
        } else {
            return "args.get(" + i + ")";
        }
    }

    public String toString() {
        String result = "\t\t\tif (true";
        Map<Identifier, String> args = new HashMap<>();
        for (int i = 0; i < arguments.size(); i++) {
            Expression arg = arguments.get(i);
            if (arg instanceof Int) {
                result += " && ((int) " + getArgName(i) + ") == " + ((Int) arg).getValue();
            } else if (arg instanceof Identifier) {
                args.put((Identifier) arg, getArgName(i));
            } else {
                throw new AssertionError();
            }
        }
        result += ") {\n" +
                "\t\t\t\treturn " + value.generateCode(args) + ";\n" +
                "\t\t\t}\n";
        return result;
    }
}
