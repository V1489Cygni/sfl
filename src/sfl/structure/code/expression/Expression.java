package sfl.structure.code.expression;

import java.util.Map;

public abstract class Expression {
    public abstract String generateCode(Map<Identifier, String> ids);
}
