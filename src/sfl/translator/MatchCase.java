package sfl.translator;

import sfl.structure.code.expression.Expression;
import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Implication;
import sfl.structure.type.Type;

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

    public void process() {
        for (int i = 0; i < arguments.size(); i++) {
            arguments.set(i, arguments.get(i).process());
        }
        value = value.process();
    }

    private String getArgCode(int i, boolean o) {
        if (o) {
            return "arg" + i;
        } else if (i == arguments.size() - 1) {
            return "o";
        } else {
            return "args.get(" + i + ")";
        }
    }

    public String generate(boolean o, Type type, ProcessedProgram program) throws TranslationException {
        List<String> constraints = new ArrayList<>();
        Map<Identifier, String> codes = new HashMap<>();
        Map<Identifier, Type> context = new HashMap<>();
        for (int i = 0; i < arguments.size(); i++) {
            if (type instanceof Implication) {
                arguments.get(i).getContext(constraints, codes, context, getArgCode(i, o), program, ((Implication) type).getArguments().get(0));
                type = ((Implication) type).apply(((Implication) type).getArguments().get(0));
            } else {
                throw new TranslationException("Wrong type");
            }
        }
        String result = "if (true";
        for (String s : constraints) {
            result += " && " + s;
        }
        result += ") {\n" +
                "return " + value.generate(codes, context, program) + ";\n" +
                "}\n";
        return result;
    }

    public Expression getValue() {
        return value;
    }
}
