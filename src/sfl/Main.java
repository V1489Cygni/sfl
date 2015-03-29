package sfl;

import sfl.structure.code.Statement;
import sfl.structure.parser.ParseException;
import sfl.structure.parser.Parser;
import sfl.translator.TranslationException;
import sfl.translator.Translator;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        for (String fileName : args) {
            try {
                if (fileName.length() < 5 || !fileName.substring(fileName.length() - 4).equals(".sfl")) {
                    System.err.println("Error while processing file " + fileName + ": Unrecognized extension");
                    continue;
                }
                File file = new File(fileName);
                new Parser(new BufferedReader(new FileReader(file)));
                List<Statement> program = Parser.Program();
                String result = Translator.translate(program, file.getName().substring(0, file.getName().length() - 4));
                BufferedWriter writer = new BufferedWriter(new FileWriter(fileName.substring(0, fileName.length() - 4) + ".java"));
                writer.write(result);
                writer.flush();
                writer.close();
            } catch (ParseException | TranslationException | IOException e) {
                System.err.println("Error while processing file " + fileName + ": " + e.getMessage());
            }
        }
    }
}
