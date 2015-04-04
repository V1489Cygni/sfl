package sfl.structure.type;

import sfl.translator.ProcessedProgram;

public enum BaseType implements Type {
    Integer, Boolean, Character, String;

    @Override
    public Type process(ProcessedProgram program) {
        return this;
    }
}