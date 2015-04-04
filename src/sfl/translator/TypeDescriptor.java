package sfl.translator;

import sfl.Main;
import sfl.structure.code.expression.Constructor;
import sfl.structure.type.Implication;
import sfl.structure.type.Type;
import sfl.structure.type.TypeIdentifier;

import java.util.ArrayList;
import java.util.List;

public class TypeDescriptor implements Loadable {
    private ProcessedProgram program;
    private TypeIdentifier name;
    private List<Constructor> constructors;
    private List<List<Type>> arguments;

    public TypeDescriptor(TypeIdentifier name, List<Constructor> constructors, List<List<Type>> arguments) {
        this.name = name;
        this.constructors = constructors;
        this.arguments = arguments;
    }

    public Type getType(Constructor constructor) throws TranslationException {
        for (int i = 0; i < constructors.size(); i++) {
            if (constructor.equals(constructors.get(i))) {
                List<Type> types = new ArrayList<>();
                types.addAll(arguments.get(i));
                types.add(name);
                return new Implication(types).process(program);
            }
        }
        throw new AssertionError();
    }

    public void process(ProcessedProgram program) throws TranslationException {
        this.program = program;
        for (int i = 0; i < arguments.size(); i++) {
            for (int j = 0; j < arguments.get(i).size(); j++) {
                arguments.get(i).set(j, arguments.get(i).get(j).process(program));
            }
        }
    }

    public boolean contains(Constructor constructor) {
        return constructors.contains(constructor);
    }

    public String generate() {
        String result = "";
        for (int i = 0; i < constructors.size(); i++) {
            result += "public static class " + constructors.get(i) + " implements Function {\n" +
                    "public java.util.List<Object> args = new java.util.ArrayList<>();\n";
            int n = arguments.get(i).size();
            for (int j = 0; j <= n; j++) {
                result += "public " + constructors.get(i) + "(";
                for (int k = 0; k < j - 1; k++) {
                    result += "Object arg" + k + ", ";
                }
                if (j != 0) {
                    result += "Object arg" + (j - 1);
                }
                result += ") {\n";
                for (int k = 0; k < j; k++) {
                    result += "args.add(arg" + k + ");\n";
                }
                result += "}\n";
            }
            result += "public " + constructors.get(i) + "(" + constructors.get(i) + " f) {\n" +
                    "args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));\n" +
                    "}\n" +
                    "public Object apply(Object o) {\n" +
                    constructors.get(i) + " f = new " + constructors.get(i) + "(this);\n" +
                    "f.args.add(o);\n" +
                    "return f;\n" +
                    "}\n" +
                    "}\n";
        }
        return result;
    }
}
