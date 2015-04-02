package sfl.translator;

import sfl.structure.code.Module;
import sfl.structure.code.expression.Identifier;
import sfl.structure.type.TypeIdentifier;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ProcessedProgram {
    private File file;
    private Module module;
    private List<Module> imports;
    private Map<Identifier, Descriptor> functions;
    private Map<TypeIdentifier, TypeDescriptor> types;

    public ProcessedProgram(File file, Module module, List<Module> imports, Map<Identifier, Descriptor> functions, Map<TypeIdentifier, TypeDescriptor> types) {
        this.file = file;
        this.module = module;
        this.imports = imports;
        this.functions = functions;
        this.types = types;
    }

    public File getFile() {
        return file;
    }

    public Module getModule() {
        return module;
    }

    public List<Module> getImports() {
        return imports;
    }

    public Map<Identifier, Descriptor> getFunctions() {
        return functions;
    }

    public Map<TypeIdentifier, TypeDescriptor> getTypes() {
        return types;
    }

    public String generate() throws TranslationException {
        String result = "/*Auto-generated code*/\n";
        if (!module.getQualifier().isEmpty()) {
            result += "package " + module.getQualifier().getPackage() + ";\n";
        }
        result += "import java.util.List;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.stream.Collectors;\n" +
                "import sfl.util.Function;\n";
        for (Module m : imports) {
            result += "import static " + m + ".*;\n";
        }
        result += "public class " + module.getValue() + " {\n";
        for (TypeDescriptor descriptor : types.values()) {
            result += descriptor.generate();
        }
        for (Descriptor descriptor : functions.values()) {
            result += descriptor.generate(this);
        }
        if (functions.containsKey(new Identifier(null, "main"))) {
            Descriptor descriptor = functions.get(new Identifier(null, "main"));
            if (descriptor.getArgsNumber() == 0) {
                result += "\n public static void main(String[] args) {\n" +
                        " System.out.println(main);\n" +
                        " }\n";
            }
        }
        result += "}";
        return result;
    }
}
