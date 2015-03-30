package sfl.translator;

import sfl.structure.code.*;
import sfl.structure.code.expression.Identifier;
import sfl.structure.type.TypeIdentifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator {
    public static String translate(List<Statement> program, String className) throws TranslationException {
        String pkg = "", nm;
        List<String> imports;
        if (program.get(0) instanceof Header) {
            String md = ((Header) program.get(0)).getModule();
            imports = ((Header) program.get(0)).getImports();
            program = program.subList(1, program.size());
            int ind = md.lastIndexOf('.');
            if (ind == -1) {
                nm = md;
            } else {
                pkg = md.substring(0, ind);
                nm = md.substring(ind + 1);
            }
            if (!nm.equals(className)) {
                throw new TranslationException("Module name must be equal to file name");
            }
        } else {
            throw new AssertionError();
        }
        Map<Identifier, Descriptor> descriptors = new HashMap<>();
        Map<TypeIdentifier, TypeDeclaration> types = new HashMap<>();
        for (Statement statement : program) {
            if (statement instanceof Declaration) {
                Identifier name = ((Declaration) statement).getName();
                if (!descriptors.containsKey(name)) {
                    descriptors.put(name, new Descriptor(name));
                }
                Descriptor descriptor = descriptors.get(name);
                descriptor.setDeclaration(((Declaration) statement).getType());
            } else if (statement instanceof Definition) {
                Identifier name = ((Definition) statement).getName();
                if (!descriptors.containsKey(name)) {
                    descriptors.put(name, new Descriptor(name));
                }
                Descriptor descriptor = descriptors.get(name);
                descriptor.addMatchCase(new MatchCase(((Definition) statement).getArguments(), ((Definition) statement).getValue()));
            } else if (statement instanceof TypeDeclaration) {
                if (types.containsKey(((TypeDeclaration) statement).getName())) {
                    throw new TranslationException("Multiple declaration of type " + ((TypeDeclaration) statement).getName());
                }
                types.put(((TypeDeclaration) statement).getName(), (TypeDeclaration) statement);
            } else {
                throw new AssertionError();
            }
        }
        String result = "/*Auto-generated code.*/\n\n";
        result += pkg.equals("") ? "" : "package " + pkg + ";\n\n";
        result += "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "import java.util.stream.Collectors;\n" +
                "import util.Function;\n" +
                "\n";
        for (String s : imports) {
            result += "import static " + s + ".*;\n";
        }
        result += "\npublic class " + className + " {\n";
        boolean need = false;
        for (Descriptor descriptor : descriptors.values()) {
            descriptor.check();
            result += (need ? "\n" : "") + descriptor.generate();
            need = true;
        }
        for (TypeDeclaration declaration : types.values()) {
            result += "\n" + declaration;
        }
        if (descriptors.containsKey(new Identifier("main"))) {
            Descriptor descriptor = descriptors.get(new Identifier("main"));
            if (descriptor.getArgsNumber() == 0) {
                result += "\n    public static void main(String[] args) {\n" +
                        "        System.out.println(main);\n" +
                        "    }\n";
            }
        }
        result += "}";
        return result;
    }
}
