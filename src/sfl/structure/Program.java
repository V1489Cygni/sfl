package sfl.structure;

import sfl.structure.code.Module;
import sfl.structure.code.expression.Constructor;
import sfl.structure.code.expression.Identifier;
import sfl.structure.type.Type;
import sfl.structure.type.TypeIdentifier;
import sfl.translator.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {
    private Module module;
    private List<Module> imports;
    private List<Statement> program;

    public Program(Module module, List<Module> imports, List<Statement> program) {
        this.module = module;
        this.imports = imports;
        this.program = program;
    }

    public ProcessedProgram process(File file) throws TranslationException {
        Map<Identifier, Descriptor> functions = new HashMap<>();
        Map<TypeIdentifier, TypeDescriptor> types = new HashMap<>();
        for (Statement statement : program) {
            if (statement instanceof Declaration) {
                Identifier name = ((Declaration) statement).getName();
                if (!functions.containsKey(name)) {
                    functions.put(name, new Descriptor(name));
                }
                Descriptor descriptor = functions.get(name);
                descriptor.setDeclaration(((Declaration) statement).getType());
            } else if (statement instanceof Definition) {
                Identifier name = ((Definition) statement).getName();
                if (!functions.containsKey(name)) {
                    functions.put(name, new Descriptor(name));
                }
                Descriptor descriptor = functions.get(name);
                descriptor.addMatchCase(new MatchCase(((Definition) statement).getArguments(), ((Definition) statement).getValue()));
            } else if (statement instanceof TypeDeclaration) {
                if (types.containsKey(((TypeDeclaration) statement).getName())) {
                    throw new TranslationException("Multiple declaration of type " + ((TypeDeclaration) statement).getName());
                }
                TypeIdentifier name = ((TypeDeclaration) statement).getName();
                List<Constructor> constructors = ((TypeDeclaration) statement).getConstructors();
                List<List<Type>> arguments = ((TypeDeclaration) statement).getArguments();
                types.put(((TypeDeclaration) statement).getName(), new TypeDescriptor(name, constructors, arguments));
            } else {
                throw new AssertionError();
            }
        }
        return new ProcessedProgram(file, module, imports, functions, types);
    }
}
