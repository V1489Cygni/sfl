package sfl.structure.type;

import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.List;

public class Implication implements Type {
    private List<Type> arguments;

    public Implication(List<Type> arguments) {
        this.arguments = arguments;
    }

    public Type head() {
        return arguments.get(0);
    }

    public Type apply(Type t, String errorPlace) throws TranslationException {
        if (t.equals(arguments.get(0))) {
            if (arguments.size() == 2) {
                return arguments.get(1);
            }
            return new Implication(arguments.subList(1, arguments.size()));
        }
        throw new TranslationException("Failed to unify types " + t + " and " + arguments.get(0) + " in " + errorPlace);
    }

    public Type tail() {
        if (arguments.size() == 2) {
            return arguments.get(1);
        }
        return new Implication(arguments.subList(1, arguments.size()));
    }

    @Override
    public Type process(ProcessedProgram program) throws TranslationException {
        while (arguments.get(arguments.size() - 1) instanceof Implication) {
            Implication im = (Implication) arguments.remove(arguments.size() - 1);
            for (int i = 0; i < im.arguments.size(); i++) {
                arguments.add(im.arguments.get(i));
            }
        }
        for (int i = 0; i < arguments.size(); i++) {
            arguments.set(i, arguments.get(i).process(program));
        }
        if (arguments.size() == 1) {
            return arguments.get(0);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Implication && arguments.equals(((Implication) o).arguments);
    }

    @Override
    public int hashCode() {
        return arguments.hashCode();
    }

    @Override
    public String toString() {
        String result = arguments.get(0).toString();
        for (int i = 1; i < arguments.size(); i++) {
            result += " -> " + arguments.get(i);
        }
        return result;
    }
}
