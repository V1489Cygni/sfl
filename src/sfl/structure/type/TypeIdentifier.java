package sfl.structure.type;

import sfl.structure.code.Module;

public class TypeIdentifier implements Type {
    private Module module;
    private String value;

    public TypeIdentifier(Module module, String value) {
        this.module = module;
        this.value = value;
    }

    @Override
    public Type process() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypeIdentifier && value.equals(((TypeIdentifier) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
