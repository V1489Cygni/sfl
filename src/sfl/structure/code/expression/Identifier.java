package sfl.structure.code.expression;

import sfl.Main;
import sfl.structure.code.Module;
import sfl.structure.type.Type;
import sfl.translator.Descriptor;
import sfl.translator.ProcessedProgram;
import sfl.translator.Searchable;
import sfl.translator.TranslationException;

import java.util.List;
import java.util.Map;

public class Identifier implements Expression, Searchable {
    private Module module;
    private String value;

    public Identifier(Module module, String value) {
        this.module = module;
        this.value = value;
    }

    @Override
    public Expression process() {
        return this;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException {
        if (context.containsKey(this)) {
            return context.get(this);
        }
        return ((Descriptor) Main.loadDescriptor(this, program)).getType();
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        codes.put(this, myCode);
        context.put(this, myType);
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException {
        if (codes.containsKey(this)) {
            return codes.get(this);
        }
        Descriptor d = (Descriptor) Main.loadDescriptor(this, program);
        if (d.getArgsNumber() == 0) {
            return this + "()";
        }
        return "new " + this + "()";
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Identifier && value.equals(((Identifier) o).value);
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
