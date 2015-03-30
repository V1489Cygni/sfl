package sfl.translator;

import sfl.structure.code.expression.Expression;
import sfl.structure.code.expression.Identifier;

import java.util.ArrayList;
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

    public String generate() throws TranslationException {
        String result = "            if (true";
        Map<Identifier, String> args = new HashMap<>();
        List<String> constraints = new ArrayList<>();
        for (int i = 0; i < arguments.size(); i++) {
            arguments.get(i).getConstraints(constraints, args, getArgName(i));
        }
        for (String s : constraints) {
            result += " && " + s;
        }
        result += ") {\n" +
                "                return " + value.generateCode(args) + ";\n" +
                "            }\n";
        return result;
    }

    public Expression getValue() {
        return value;
    }
}
