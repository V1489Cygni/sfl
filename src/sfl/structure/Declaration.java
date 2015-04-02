package sfl.structure;

import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Type;

public class Declaration implements Statement {
    private Identifier name;
    private Type type;

    public Declaration(Identifier name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Identifier getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return name + " :: " + type;
    }
}
