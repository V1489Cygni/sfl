package sfl.structure.code.expression;

import java.util.Map;

public class Identifier extends Expression {
    private String value;

    public Identifier(String value) {
        this.value = value;
    }

    @Override
    public String generateCode(Map<Identifier, String> ids) {
        if (ids.containsKey(this)) {
            return ids.get(this);
        } else {
            return "new " + value + "()";
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Identifier && value.equals(((Identifier) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
