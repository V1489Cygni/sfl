package sfl.translator;

import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Type;

import java.util.ArrayList;
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

    private String generateCode() {
        String result = "\t\t\tif (args.size() < " + (argsNumber - 1) + ") {\n" +
                "\t\t\t\t" + name + " f = new " + name + "(this);\n" +
                "\t\t\t\tf.args.add(o);\n" +
                "\t\t\t\treturn f;\n" +
                "\t\t\t}\n";
        for (MatchCase matchCase : cases) {
            result += matchCase;
        }
        result += "\t\t\tthrow new AssertionError();\n";
        return result;
    }

    @Override
    public String toString() {
        String result = "\tpublic static class " + name + " implements Function {\n" +
                "\t\tpublic List<Object> args = new ArrayList<>();\n" +
                "\n" +
                "\t\tpublic " + name + "() {\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tpublic " + name + "(" + name + " f) {\n" +
                "\t\t\tfor (Object o : f.args) {\n" +
                "\t\t\t\targs.add(o);\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t@Override\n" +
                "\t\tpublic Object apply(Object o) {\n" +
                generateCode() +
                "\t\t}\n" +
                "\t}\n";
        return result;
    }
}
