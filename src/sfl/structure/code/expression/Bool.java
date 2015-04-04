package sfl.structure.code.expression;

import sfl.structure.type.BaseType;
import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public class Bool implements Expression {
    private boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) {
        return BaseType.Boolean;
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        if (!myType.equals(BaseType.Boolean)) {
            throw new TranslationException("Failed to unify types " + myType + " and " + BaseType.Integer);
        }
        constraints.add("((boolean) " + myCode + ") == " + value);
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) {
        return Boolean.toString(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
