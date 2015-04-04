package sfl.structure.type;

import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

public interface Type {
    Type process(ProcessedProgram program) throws TranslationException;
}
