package sfl.structure.code.expression;

import java.util.Map;

public class Bool extends BaseExpression {
    private boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public String generateCode(Map<Identifier, String> ids) {
        return Boolean.toString(value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
