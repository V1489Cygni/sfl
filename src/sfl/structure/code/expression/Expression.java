package sfl.structure.code.expression;

import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public interface Expression {
    Expression process();

    Type getType(Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException;

    void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException;

    String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException;
}
