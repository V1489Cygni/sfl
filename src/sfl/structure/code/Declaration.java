package sfl.structure.code;

import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Type;

public class Declaration extends Statement {
    private Identifier name;
    private Type type;

    public Declaration(Identifier name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public Identifier getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " :: " + type;
    }
}
