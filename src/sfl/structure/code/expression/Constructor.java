package sfl.structure.code.expression;

import sfl.Main;
import sfl.structure.code.Module;
import sfl.structure.type.Type;
import sfl.translator.ProcessedProgram;
import sfl.translator.Searchable;
import sfl.translator.TranslationException;
import sfl.translator.TypeDescriptor;

import java.util.List;
import java.util.Map;

public class Constructor implements Expression, Searchable {
    private Module module;
    private String value;

    public Constructor(Module module, String value) {
        this.module = module;
        this.value = value;
    }

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException {
        return ((TypeDescriptor) Main.loadDescriptor(this, program)).getType(this);
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        constraints.add(myCode + " instanceof " + value);
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) {
        return "new " + this + "()";
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Constructor && value.equals(((Constructor) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return (module.isEmpty() ? "" : module.getClassName() + ".") + value;
    }
}
