package sfl.translator;

import sfl.structure.code.Module;
import sfl.structure.code.expression.Constructor;
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

    public ProcessedProgram(File file, Module module, List<Module> imports, Map<Identifier, Descriptor> functions, Map<TypeIdentifier, TypeDescriptor> types) throws TranslationException {
        this.file = file;
        this.module = module;
        this.imports = imports;
        this.functions = functions;
        this.types = types;
        for (Descriptor descriptor : functions.values()) {
            descriptor.process(this);
        }
        for (TypeDescriptor descriptor : types.values()) {
            descriptor.process(this);
        }
    }

    public String generate() throws TranslationException {
        String result = "/*Auto-generated code*/\n";
        if (!module.hasEmptyPackage()) {
            result += "package " + module.getPackage() + ";\n";
        }
        result += "import sfl.util.Function;\n";
        for (Module m : imports) {
            result += "import static " + m.getClassName() + ".*;\n";
        }
        result += "public class " + module.getValue() + " {\n";
        for (TypeDescriptor descriptor : types.values()) {
            result += descriptor.generate();
        }
        for (Descriptor descriptor : functions.values()) {
            result += descriptor.generate();
        }
        if (functions.containsKey(new Identifier(null, "main"))) {
            Descriptor descriptor = functions.get(new Identifier(null, "main"));
            if (descriptor.getArgsNumber() == 0) {
                result += "public static void main(String[] args) {\n" +
                        "System.out.println(main());\n" +
                        "}\n";
            }
        }
        result += "}";
        return result;
    }

    public Loadable find(Searchable searchable) {
        if (searchable instanceof Identifier) {
            return functions.get(searchable);
        } else if (searchable instanceof Constructor) {
            for (TypeDescriptor descriptor : types.values()) {
                if (descriptor.contains((Constructor) searchable)) {
                    return descriptor;
                }
            }
            return null;
        } else {
            throw new AssertionError();
        }
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

    public Map<TypeIdentifier, TypeDescriptor> getTypes() {
        return types;
    }
}
