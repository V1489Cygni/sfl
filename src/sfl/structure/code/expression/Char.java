package sfl.structure.code.expression;

import sfl.structure.type.BaseType;
import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public class Char implements Expression {
    private char value;

    public Char(char value) {
        this.value = value;
    }

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) {
        return BaseType.Character;
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        if (!myType.equals(BaseType.Character)) {
            throw new TranslationException("Failed to unify types " + myType + " and " + BaseType.Character);
        }
        constraints.add("((char) " + myCode + ") == '" + value + "'");
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) {
        return "'" + Character.toString(value) + "'";
    }

    @Override
    public String toString() {
        return "'" + Character.toString(value) + "'";
    }
}
