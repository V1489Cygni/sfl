package sfl.structure.code.expression;

import java.util.List;
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
    public void getConstraints(List<String> constraints, Map<Identifier, String> codes, String myCode) {
        constraints.add(myCode + " == " + value);
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }
}
