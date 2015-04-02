package sfl.structure.type;

public enum BaseType implements Type {
    Integer, Boolean, Character, String;

    @Override
    public Type process() {
        return this;
    }
}