package sfl.structure.code.expression;

import java.util.List;
import java.util.Map;

public class TypeConstructor extends Expression {
    private String value;

    public TypeConstructor(String value) {
        this.value = value;
    }

    @Override
    public String generateCode(Map<Identifier, String> ids) {
        return "new " + value + "()";
    }

    @Override
    public void getConstraints(List<String> constraints, Map<Identifier, String> codes, String myCode) {
        constraints.add(myCode + " instanceof " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
