package sfl.structure.code.expression;

import sfl.structure.type.BaseType;
import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public class Str implements Expression {
    private String value;

    public Str(String value) {
        this.value = value;
    }

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) {
        return BaseType.String;
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        if (!myType.equals(BaseType.String)) {
            throw new TranslationException("Wrong type");
        }
        constraints.add("((String) " + myCode + ").equals(" + value + ")");
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) {
        return "\"" + value + "\"";
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}