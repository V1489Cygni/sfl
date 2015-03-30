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

    public void setDeclaration(Type type) throws TranslationException {
        if (this.type != null) {
            throw new TranslationException("Multiple declaration of " + name);
        }
        this.type = type;
    }

    public void addMatchCase(MatchCase matchCase) {
        cases.add(matchCase);
    }

    public void check() throws TranslationException {
        if (cases.isEmpty()) {
            throw new TranslationException("Function " + name + " has no definition");
        }
        argsNumber = cases.get(0).getArgsNumber();
        for (int i = 1; i < cases.size(); i++) {
            if (argsNumber != cases.get(i).getArgsNumber()) {
                throw new TranslationException("Function " + name + " is inconsistent");
            }
        }
    }

    private String generateCode() throws TranslationException {
        String result = "            if (args.size() < " + (argsNumber - 1) + ") {\n" +
                "                " + name + " f = new " + name + "(this);\n" +
                "                f.args.add(o);\n" +
                "                return f;\n" +
                "            }\n";
        for (MatchCase matchCase : cases) {
            result += matchCase.generate();
        }
        result += "            throw new AssertionError();\n";
        return result;
    }

    public String generate() throws TranslationException {
        if (argsNumber == 0) {
            return "    public static Object " + name + " = " + cases.get(0).getValue().generateCode(new HashMap<>()) + ";\n";
        }
        String result = "    public static class " + name + " implements Function {\n" +
                "        public List<Object> args = new ArrayList<>();\n" +
                "\n" +
                "        public " + name + "() {\n" +
                "        }\n" +
                "\n" +
                "        public " + name + "(" + name + " f) {\n" +
                "            for (Object o : f.args) {\n" +
                "                args.add(o);\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public Object apply(Object o) {\n" +
                generateCode() +
                "        }\n" +
                "    }\n";
        return result;
    }
}
