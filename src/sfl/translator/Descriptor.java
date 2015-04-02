package sfl.translator;

import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Descriptor {
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
            throw new TranslationException("Multiple declaration of " + name);
        }
        this.type = type;
    }

    public void addMatchCase(MatchCase matchCase) {
        cases.add(matchCase);
    }

    public void process() throws TranslationException {
        if (cases.isEmpty()) {
            throw new TranslationException("Function " + name + " has no definition");
        }
        argsNumber = cases.get(0).getArgsNumber();
        for (int i = 1; i < cases.size(); i++) {
            if (argsNumber != cases.get(i).getArgsNumber()) {
                throw new TranslationException("Function " + name + " is inconsistent");
            }
        }
        type = type.process();
        for (MatchCase matchCase : cases) {
            matchCase.process();
        }
    }

    public String generate(ProcessedProgram program) throws TranslationException {
        if (argsNumber == 0) {
            return " public static Object " + name + " = " + cases.get(0).getValue().generate(new HashMap<>(), new HashMap<>(), program) + ";\n";
        }
        String result = "public static class " + name + " implements Function {\n" +
                "public static List<Object> args = new ArrayList<>();\n";
        for (int i = 0; i < argsNumber; i++) {
            result += "public " + name + "(";
            for (int j = 0; j < i; j++) {
                result += "Object arg" + j + ", ";
            }
            result += "Object arg" + i + ") {\n";
            for (int j = 0; j <= i; j++) {
                result += "args.add(arg" + j + ");\n";
            }
            result += "}\n";
        }
        result += "public " + name + "(" + name + " f) {\n" +
                "args.addAll(f.args.stream().collect(Collectors.toList()));\n" +
                "}\n" +
                "public Object apply(Object o) {\n" +
                "if (args.size() < " + (argsNumber - 1) + ") {\n" +
                name + " f = new " + name + "(this);\n" +
                "f.args.add(o);\n" +
                "return f;\n" +
                "}\n";
        for (MatchCase matchCase : cases) {
            result += matchCase.generate(false, type, program);
        }
        result += "throw new AssertionError();\n" +
                "}\n" +
                "}\n";
        result += "public static Object " + name + "(";
        for (int i = 0; i < argsNumber - 1; i++) {
            result += "Object arg" + i + ", ";
        }
        result += "Object arg" + (argsNumber - 1) + ") {\n";
        for (MatchCase matchCase : cases) {
            result += matchCase.generate(true, type, program);
        }
        result += "throw new AssertionError();\n" +
                "}\n";
        return result;
    }
}
