package sfl.translator;

import sfl.structure.code.Declaration;
import sfl.structure.code.Definition;
import sfl.structure.code.Statement;
import sfl.structure.code.expression.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator {
    public static String translate(List<Statement> program, String className) throws TranslationException {
        Map<Identifier, Descriptor> descriptors = new HashMap<>();
        for (Statement statement : program) {
            Identifier name = statement.getName();
            if (!descriptors.containsKey(name)) {
                descriptors.put(name, new Descriptor(name));
            }
            Descriptor descriptor = descriptors.get(name);
            if (statement instanceof Declaration) {
                descriptor.setDeclaration(((Declaration) statement).getType());
            } else if (statement instanceof Definition) {
                descriptor.addMatchCase(new MatchCase(((Definition) statement).getArguments(), ((Definition) statement).getValue()));
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
            result += (need ? "\n" : "") + descriptor;
            need = true;
        }
        result += "}";
        return result;
    }
}
