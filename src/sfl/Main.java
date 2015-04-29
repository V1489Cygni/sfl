package sfl;

import sfl.structure.code.Module;
import sfl.structure.parser.ParseException;
import sfl.structure.parser.Parser;
import sfl.structure.type.TypeIdentifier;
import sfl.translator.*;

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
            } catch (ParseException | IOException | TranslationException e) {
                System.err.println("Error while processing file " + fileName + ": " + e.getMessage());
            }
        }
    }

    public static Module findModule(TypeIdentifier identifier, ProcessedProgram program) throws TranslationException {
        Map<TypeIdentifier, TypeDescriptor> types = program.getTypes();
        for (TypeIdentifier id : types.keySet()) {
            if (id.valueEquals(identifier)) {
                return program.getModule();
            }
        }
        File file = program.getModule().remove(program.getFile());
        for (Module m : program.getImports()) {
            try {
                ProcessedProgram p = loadProgram(m.append(file));
                types = p.getTypes();
                for (TypeIdentifier id : types.keySet()) {
                    if (id.valueEquals(identifier)) {
                        return p.getModule();
                    }
                }
            } catch (ParseException | TranslationException | IOException e) {
                throw new TranslationException("Error while loading module " + m + ": " + e.getMessage());
            }
        }
        throw new TranslationException("Undefined reference to " + identifier);
    }

    public static Loadable loadDescriptor(Searchable searchable, ProcessedProgram program) throws TranslationException {
        Module module = searchable.getModule();
        File file = program.getModule().remove(program.getFile());
        if (module.isEmpty()) {
            Loadable loadable = program.find(searchable);
            if (loadable != null) {
                return loadable;
            }
            for (Module m : program.getImports()) {
                try {
                    loadable = loadProgram(m.append(file)).find(searchable);
                    if (loadable != null) {
                        return loadable;
                    }
                } catch (ParseException | TranslationException | IOException e) {
                    throw new TranslationException("Error while loading module " + m + ": " + e.getMessage());
                }
            }
        } else {
            try {
                Loadable loadable = loadProgram(module.append(file)).find(searchable);
                if (loadable != null) {
                    return loadable;
                }
            } catch (FileNotFoundException | ParseException e) {
                throw new TranslationException("Error while loading module " + module + ": " + e.getMessage());
            }
        }
        throw new TranslationException("Undefined reference to: " + searchable);
    }

    private static ProcessedProgram loadProgram(File file) throws FileNotFoundException, ParseException, TranslationException {
        if (loaded.containsKey(file)) {
            return loaded.get(file);
        }
        ProcessedProgram program = new Parser(new BufferedReader(new FileReader(file))).Program().process(file);
        String fileName = file.getName();
        if (!fileName.substring(0, fileName.length() - 4).equals(program.getModule().getValue())) {
            throw new TranslationException("Error in file " + file + ": module name must be equal to file name");
        }
        loaded.put(file, program);
        return program;
    }
}
