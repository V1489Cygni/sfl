package sfl.structure.type;

import sfl.Main;
import sfl.structure.code.Module;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

public class TypeIdentifier implements Type {
    private Module module;
    private String value;

    public TypeIdentifier(Module module, String value) {
        this.module = module;
        this.value = value;
    }

    @Override
    public Type process(ProcessedProgram program) throws TranslationException {
        if (module.isEmpty()) {
            module = Main.findModule(this, program);
        }
        return this;
    }

    public boolean valueEquals(TypeIdentifier identifier) {
        return value.equals(identifier.value);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypeIdentifier && value.equals(((TypeIdentifier) o).value) && module.equals(((TypeIdentifier) o).module);
    }

    @Override
    public int hashCode() {
        return value.hashCode() + module.hashCode();
    }

    @Override
    public String toString() {
        return (module.isEmpty() ? "" : module + ".") + value;
    }
}