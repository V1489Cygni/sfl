package sfl.structure.code.expression;

import sfl.Main;
import sfl.structure.type.BaseType;
import sfl.structure.type.Implication;
import sfl.structure.type.Type;
import sfl.translator.Descriptor;
import sfl.translator.ProcessedProgram;
import sfl.translator.TranslationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sfl.structure.type.BaseType.Boolean;
import static sfl.structure.type.BaseType.Character;
import static sfl.structure.type.BaseType.Integer;
import static sfl.structure.type.BaseType.String;

public class Application implements Expression {
    private List<Expression> arguments;

    public Application(List<Expression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public Expression process() {
        Application result = this;
        while (result.arguments.get(0) instanceof Application) {
            for (int i = 1; i < result.arguments.size(); i++) {
                ((Application) result.arguments.get(0)).arguments.add(result.arguments.get(i));
            }
            result = (Application) result.arguments.get(0);
        }
        for (int i = 0; i < result.arguments.size(); i++) {
            result.arguments.set(i, result.arguments.get(i).process());
        }
        if (result.arguments.size() == 1) {
            return result.arguments.get(0);
        }
        return result;
    }

    @Override
    public Type getType(Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException {
        if (arguments.get(0) instanceof Special) {
            Expression op = arguments.get(0);
            if (arguments.size() != 3) {
                if (arguments.size() == 2 && op.equals(Special.Not) && arguments.get(1).getType(context, program).equals(Boolean)) {
                    return Boolean;
                }
                throw new TranslationException("Incorrect expression: " + this);
            }
            Type t1 = arguments.get(1).getType(context, program), t2 = arguments.get(2).getType(context, program);
            if (op.equals(Special.Sub) || op.equals(Special.Mul) || op.equals(Special.Div) || op.equals(Special.Mod)) {
                if (t1.equals(t2) && (t1.equals(Character) || t1.equals(Integer))) {
                    return t1;
                }
            }
            if (op.equals(Special.Add) && t1.equals(t2) && (t1.equals(Character) || t1.equals(Integer) || t1.equals(String))) {
                return t1;
            }
            if (op.equals(Special.Add) && (t1.equals(String) || t2.equals(String))) {
                return String;
            }
            if (op.equals(Special.Eq) && t1.equals(t2)) {
                return Boolean;
            }
            if (op.equals(Special.Ls) || op.equals(Special.Gr) || op.equals(Special.Lse) || op.equals(Special.Gre)) {
                if (t1.equals(t2) && (t1.equals(Character) || t1.equals(Integer))) {
                    return Boolean;
                }
            }
            if (op.equals(Special.At) && t1.equals(String) && t2.equals(Integer)) {
                return Character;
            }
            throw new TranslationException("Incorrect expression: " + this);
        }
        Type t = arguments.get(0).getType(context, program);
        for (int i = 1; i < arguments.size(); i++) {
            if (t instanceof Implication) {
                t = ((Implication) t).apply(arguments.get(i).getType(context, program), this.toString());
            } else {
                throw new TranslationException("Trying to apply argument to a non function expression: " + this);
            }
        }
        return t;
    }

    @Override
    public void getContext(List<String> constraints, Map<Identifier, String> codes, Map<Identifier, Type> context, String myCode, ProcessedProgram program, Type myType) throws TranslationException {
        if (arguments.get(0) instanceof Constructor) {
            Type t = arguments.get(0).getType(new HashMap<>(), program);
            arguments.get(0).getContext(constraints, codes, context, myCode, program, t);
            for (int i = 1; i < arguments.size(); i++) {
                if (t instanceof Implication) {
                    arguments.get(i).getContext(constraints, codes, context, "((" + arguments.get(0) + ") " + myCode + ").args.get(" + (i - 1) + ")", program, ((Implication) t).head());
                    t = ((Implication) t).tail();
                } else {
                    throw new TranslationException("Trying to apply argument to a non function expression: " + this);
                }
            }
            if (!myType.equals(t)) {
                throw new TranslationException("Failed to unify types " + t + " and " + myType + " in " + this);
            }
        } else {
            throw new TranslationException("Incorrect expression: " + this);
        }
    }

    @Override
    public String generate(Map<Identifier, String> codes, Map<Identifier, Type> context, ProcessedProgram program) throws TranslationException {
        if (arguments.get(0) instanceof Special) {
            Expression op = arguments.get(0);
            if (arguments.size() != 3) {
                return "!((boolean) " + arguments.get(1).generate(codes, context, program) + ")";
            }
            Type t1 = arguments.get(1).getType(context, program), t2 = arguments.get(2).getType(context, program);
            if (op.equals(Special.Sub) || op.equals(Special.Mul) || op.equals(Special.Div) || op.equals(Special.Mod)) {
                if (t1.equals(Character)) {
                    return "((char) " + arguments.get(1).generate(codes, context, program) + ") " +
                            op.generate(codes, context, program) + " ((char) " + arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(Integer)) {
                    return "((int) " + arguments.get(1).generate(codes, context, program) + ") " +
                            op.generate(codes, context, program) + " ((int) " + arguments.get(2).generate(codes, context, program) + ")";
                }
            }
            if (op.equals(Special.Add) && t1.equals(t2)) {
                if (t1.equals(Character)) {
                    return "((char) " + arguments.get(1).generate(codes, context, program) + ") + ((char) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(Integer)) {
                    return "((int) " + arguments.get(1).generate(codes, context, program) + ") + ((int) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(String)) {
                    return "((String) " + arguments.get(1).generate(codes, context, program) + ") + ((String) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
            }
            if (op.equals(Special.Add) && (t1.equals(String) || t2.equals(String))) {
                return "((" + typeName((BaseType) t1) + ") " + arguments.get(1).generate(codes, context, program) +
                        ") + ((" + typeName((BaseType) t2) + ") " + arguments.get(2).generate(codes, context, program) + ")";
            }
            if (op.equals(Special.Eq)) {
                if (t1.equals(Boolean)) {
                    return "((boolean) " + arguments.get(1).generate(codes, context, program) + ") == ((boolean) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(Character)) {
                    return "((char) " + arguments.get(1).generate(codes, context, program) + ") == ((char) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(Integer)) {
                    return "((int) " + arguments.get(1).generate(codes, context, program) + ") == ((int) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(String)) {
                    return "((String) " + arguments.get(1).generate(codes, context, program) + ").equals((String) " +
                            arguments.get(2).generate(codes, context, program) + ")";
                }
            }
            if (op.equals(Special.Ls) || op.equals(Special.Gr) || op.equals(Special.Lse) || op.equals(Special.Gre)) {
                if (t1.equals(Character)) {
                    return "((char) " + arguments.get(1).generate(codes, context, program) + ") " +
                            generate(codes, context, program) + " ((char) " + arguments.get(2).generate(codes, context, program) + ")";
                }
                if (t1.equals(Integer)) {
                    return "((int) " + arguments.get(1).generate(codes, context, program) + ") " +
                            generate(codes, context, program) + " ((int) " + arguments.get(2).generate(codes, context, program) + ")";
                }
            }
            if (op.equals(Special.At)) {
                return "((String) " + arguments.get(1).generate(codes, context, program) + ").charAt((int) " +
                        arguments.get(2).generate(codes, context, program) + ")";
            }
            throw new TranslationException("Incorrect expression: " + this);
        }
        if (arguments.get(0) instanceof Constructor) {
            String result = "new " + arguments.get(0) + "(";
            for (int i = 1; i < arguments.size() - 1; i++) {
                result += arguments.get(i).generate(codes, context, program) + ", ";
            }
            return result + arguments.get(arguments.size() - 1).generate(codes, context, program) + ")";
        }
        if (arguments.get(0) instanceof Identifier && !codes.containsKey(arguments.get(0))) {
            Descriptor d = (Descriptor) Main.loadDescriptor((Identifier) arguments.get(0), program);
            if (arguments.size() > d.getArgsNumber()) {
                String result = arguments.get(0) + "(";
                for (int i = 1; i < d.getArgsNumber(); i++) {
                    result += arguments.get(i).generate(codes, context, program) + ", ";
                }
                result += arguments.get(d.getArgsNumber()).generate(codes, context, program) + ")";
                for (int i = d.getArgsNumber() + 1; i < arguments.size(); i++) {
                    result += "((Function) " + result + ").apply(" + arguments.get(i).generate(codes, context, program) + ")";
                }
                return result;
            } else {
                String result = "new " + arguments.get(0) + "(";
                for (int i = 1; i < arguments.size() - 1; i++) {
                    result += arguments.get(i).generate(codes, context, program) + ", ";
                }
                return result + arguments.get(arguments.size() - 1).generate(codes, context, program) + ")";
            }
        }
        String result = arguments.get(0).generate(codes, context, program);
        for (int i = 1; i < arguments.size(); i++) {
            result = "((Function) " + result + ").apply(" + arguments.get(i).generate(codes, context, program) + ")";
        }
        return result;
    }

    private String typeName(BaseType t) {
        switch (t) {
            case Integer:
                return "int";
            case Boolean:
                return "boolean";
            case Character:
                return "char";
            case String:
                return "String";
        }
        throw new AssertionError();
    }

    @Override
    public String toString() {
        String result = "(";
        for (int i = 0; i < arguments.size() - 1; i++) {
            result += arguments.get(i) + " ";
        }
        return result + arguments.get(arguments.size() - 1) + ")";
    }
}
