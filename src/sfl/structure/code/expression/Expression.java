package sfl.structure.code.expression;

import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public abstract class Expression {
    public abstract String generateCode(Map<Identifier, String> ids);

    public abstract void getConstraints(List<String> constraints, Map<Identifier, String> codes, String myCode) throws TranslationException;
}
