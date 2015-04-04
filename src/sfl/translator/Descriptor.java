package sfl.translator;

import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Type;

import java.util.ArrayList;
import java.util.List;

public class Descriptor implements Loadable {
    private ProcessedProgram program;
    private Identifier name;
    private Type type;
    private List<MatchCase> cases = new ArrayList<>();
    private int argsNumber;

    public Descriptor(Identifier name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public int getArgsNumber() {
        return argsNumber;
    }

    public void setDeclaration(Type type) throws TranslationException {
        if (this.type != null) {
            throw new TranslationException("Multiple type declarations of function: " + name);
        }
        this.type = type;
    }

    public void addMatchCase(MatchCase matchCase) {
        cases.add(matchCase);
    }

    public void process(ProcessedProgram program) throws TranslationException {
        this.program = program;
        if (type == null) {
            throw new TranslationException("Function missing type declaration: " + name);
        }
        if (cases.isEmpty()) {
            throw new TranslationException("Function missing definition: " + name);
        }
        argsNumber = cases.get(0).getArgsNumber();
        for (int i = 1; i < cases.size(); i++) {
            if (argsNumber != cases.get(i).getArgsNumber()) {
                throw new TranslationException("Match cases have different number of arguments: " + name);
            }
        }
        type = type.process(program);
        for (MatchCase matchCase : cases) {
            matchCase.process();
        }
    }

    public String generate() throws TranslationException {
        String result = "";
        if (argsNumber > 0) {
            result = "public static class " + name + " implements Function {\n" +
                    "public java.util.List<Object> args = new java.util.ArrayList<>();\n";
            for (int j = 0; j < argsNumber; j++) {
                result += "public " + name + "(";
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
            result += "public " + name + "(" + name + " f) {\n" +
                    "args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));\n" +
                    "}\n" +
                    "public Object apply(Object o) {\n" +
                    "if (args.size() < " + (argsNumber - 1) + ") {\n" +
                    name + " f = new " + name + "(this);\n" +
                    "f.args.add(o);\n" +
                    "return f;\n" +
                    "}\n";
            for (MatchCase matchCase : cases) {
                result += matchCase.generate(false, type, program, name);
            }
            result += "throw new AssertionError();\n" +
                    "}\n" +
                    "}\n";
        }
        result += "public static Object " + name + "(";
        for (int i = 0; i < argsNumber - 1; i++) {
            result += "Object arg" + i + ", ";
        }
        if (argsNumber != 0) {
            result += "Object arg" + (argsNumber - 1);
        }
        result += ") {\n";
        for (MatchCase matchCase : cases) {
            result += matchCase.generate(true, type, program, name);
        }
        result += "throw new AssertionError();\n" +
                "}\n";
        return result;
    }
}
