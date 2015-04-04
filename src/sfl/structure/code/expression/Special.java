package sfl.structure.code.expression;

import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public enum Special implements Expression {
    Add, Sub, Mul, Div, Mod, Eq, Gr, Ls, Gre, Lse, And, Or, Not, At;

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException {
        throw new TranslationException("Incorrect special expression: " + this);
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        throw new TranslationException("Functions are not allowed in match cases: " + this);
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) {
        switch (this) {
            case Add:
                return "+";
            case Sub:
                return "-";
            case Mul:
                return "*";
            case Div:
                return "/";
            case Mod:
                return "%";
            case Eq:
                return "==";
            case Gr:
                return ">";
            case Ls:
                return "<";
            case Gre:
                return ">=";
            case Lse:
                return "<=";
            case And:
                return "&&";
            case Or:
                return "||";
            case Not:
                return "!";
            case At:
                return ".charAt(";
        }
        throw new AssertionError();
    }
}
