package sfl.translator;

import sfl.structure.code.Declaration;
import sfl.structure.code.Definition;
import sfl.structure.code.Statement;
import sfl.structure.code.TypeDeclaration;
import sfl.structure.code.expression.Identifier;
import sfl.structure.type.TypeIdentifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator {
    public static String translate(List<Statement> program, String className) throws TranslationException {
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
        String result = "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "\n" +
                "public class " + className + " {\n";
        boolean need = false;
        for (Descriptor descriptor : descriptors.values()) {
            descriptor.check();
            result += (need ? "\n" : "") + descriptor.generate();
            need = true;
        }
        for (TypeDeclaration declaration : types.values()) {
            result += "\n" + declaration;
        }
        result += "}";
        return result;
    }
}
