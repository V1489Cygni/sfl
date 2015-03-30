package sfl.structure.code.expression;

import java.util.List;
import java.util.Map;

public class Int extends BaseExpression {
    private int value;

    public Int(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String generateCode(Map<Identifier, String> ids) {
        return Integer.toString(value);
    }

    @Override
    public void getConstraints(List<String> constraints, Map<Identifier, String> codes, String myCode) {
        constraints.add(myCode + " == " + value);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
