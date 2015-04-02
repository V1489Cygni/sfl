package sfl.structure.type;

import sfl.translator.TranslationException;

import java.util.List;

public class Implication implements Type {
    private List<Type> arguments;

    public Implication(List<Type> arguments) {
        this.arguments = arguments;
    }

    public List<Type> getArguments() {
        return arguments;
    }

    public Type apply(Type t) throws TranslationException {
        if (t.equals(arguments.get(0))) {
            if (arguments.size() == 2) {
                return arguments.get(1);
            }
            return new Implication(arguments.subList(1, arguments.size()));
        }
        throw new TranslationException("Wrong type");
    }

    @Override
    public Type process() {
        while (arguments.get(arguments.size() - 1) instanceof Implication) {
            for (int i = 1; i < ((Implication) arguments.get(arguments.size() - 1)).arguments.size(); i++) {
                arguments.add(((Implication) arguments.get(arguments.size() - 1)).arguments.get(i));
            }
        }
        for (int i = 0; i < arguments.size(); i++) {
            arguments.set(i, arguments.get(i).process());
        }
        return this;
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
