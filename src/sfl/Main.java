package sfl;

import sfl.structure.code.Module;
import sfl.structure.code.expression.Constructor;
import sfl.structure.code.expression.Identifier;
import sfl.structure.parser.ParseException;
import sfl.structure.parser.Parser;
import sfl.translator.Descriptor;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;
import sfl.translator.TypeDescriptor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<File, ProcessedProgram> loaded = new HashMap<>();

    public static void main(String[] args) {
        for (String fileName : args) {
            try {
                if (fileName.length() < 5 || !fileName.substring(fileName.length() - 4).equals(".sfl")) {
                    System.err.println("Error while processing file " + fileName + ": Unrecognized extension");
                    continue;
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName.substring(0, fileName.length() - 4) + ".java"));
                writer.write(loadProgram(new File(fileName)).generate());
                writer.flush();
                writer.close();
            } catch (ParseException | TranslationException | IOException e) {
                System.err.println("Error while processing file " + fileName + ": " + e.getMessage());
            }
        }
    }

    public static ProcessedProgram loadProgram(File file) throws FileNotFoundException, ParseException, TranslationException {
        if (loaded.containsKey(file)) {
            return loaded.get(file);
        }
        new Parser(new BufferedReader(new FileReader(file)));
        ProcessedProgram program = Parser.Program().process(file);
        String fileName = file.getName();
        if (!fileName.substring(0, fileName.length() - 4).equals(program.getModule().getValue())) {
            throw new TranslationException("Error in file " + file + ": module name must be equal to file name");
        }
        loaded.put(file, program);
        return program;
    }

    public static Descriptor loadDescriptor(Identifier identifier, ProcessedProgram program) throws TranslationException {
        Module module = identifier.getModule();
        int x = module.getQualifier().getSize();
        File file = program.getFile();
        for (int i = 0; i < x; i++) {
            file = file.getParentFile();
        }
        if (module.isEmpty()) {
            if (program.getFunctions().containsKey(identifier)) {
                return program.getFunctions().get(identifier);
            }
            for (Module m : program.getImports()) {
                try {
                    ProcessedProgram p = loadProgram(new File(file.getPath() + m.getFile()));
                    if (p.getFunctions().containsKey(identifier)) {
                        return p.getFunctions().get(identifier);
                    }
                } catch (ParseException | TranslationException | IOException e) {
                    throw new TranslationException("Error while loading module: " + m);
                }
            }
        } else {
            try {
                ProcessedProgram p = loadProgram(new File(file.getPath() + module.getFile()));
                if (p.getFunctions().containsKey(identifier)) {
                    return p.getFunctions().get(identifier);
                }
            } catch (FileNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
        throw new TranslationException("Undefined reference to " + identifier);
    }

    public static TypeDescriptor loadDescriptor(Constructor constructor, ProcessedProgram program) throws TranslationException {
        Module module = constructor.getModule();
        int x = module.getQualifier().getSize();
        File file = program.getFile();
        for (int i = 0; i < x; i++) {
            file = file.getParentFile();
        }
        if (module.isEmpty()) {
            for (TypeDescriptor descriptor : program.getTypes().values()) {
                if (descriptor.contains(constructor)) {
                    return descriptor;
                }
            }
            for (Module m : program.getImports()) {
                try {
                    ProcessedProgram p = loadProgram(new File(file.getPath() + m.getFile()));
                    for (TypeDescriptor descriptor : p.getTypes().values()) {
                        if (p.getFunctions().containsKey(constructor)) {
                            return descriptor;
                        }
                    }
                } catch (ParseException | TranslationException | IOException e) {
                    throw new TranslationException("Error while loading module: " + m);
                }
            }
        } else {
            try {
                ProcessedProgram p = loadProgram(new File(file.getPath() + module.getFile()));
                for (TypeDescriptor descriptor : p.getTypes().values()) {
                    if (p.getFunctions().containsKey(constructor)) {
                        return descriptor;
                    }
                }
            } catch (FileNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
        throw new TranslationException("Undefined reference to " + constructor);
    }
}
