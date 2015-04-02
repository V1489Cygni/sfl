package sfl.structure.code.expression;

import sfl.structure.type.BaseType;
import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public class Int implements Expression {
    private int value;

    public Int(int value) {
        this.value = value;
    }

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) {
        return BaseType.Integer;
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        if (!myType.equals(BaseType.Integer)) {
            throw new TranslationException("Wrong type");
        }
        constraints.add("((int) " + myCode + ") == " + value);
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) {
        return Integer.toString(value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
