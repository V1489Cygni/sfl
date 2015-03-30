package sfl.structure.code;

import sfl.structure.code.expression.TypeConstructor;
import sfl.structure.type.Type;
import sfl.structure.type.TypeIdentifier;

import java.util.List;

public class TypeDeclaration extends Statement {
    private TypeIdentifier name;
    private List<TypeConstructor> constructors;
    private List<List<Type>> arguments;

    public TypeDeclaration(TypeIdentifier name, List<TypeConstructor> constructors, List<List<Type>> arguments) {
        this.name = name;
        this.constructors = constructors;
        this.arguments = arguments;
    }

    public TypeIdentifier getName() {
        return name;
    }

    @Override
    public String toString() {
        String result = "";
        for (TypeConstructor constructor : constructors) {
            result += "    public static class " + constructor + " implements Function {\n" +
                    "        public List<Object> args = new ArrayList<>();\n" +
                    "\n" +
                    "        public " + constructor + "() {\n" +
                    "        }\n" +
                    "\n" +
                    "        public " + constructor + "(" + constructor + " f) {\n" +
                    "            for (Object o : f.args) {\n" +
                    "                args.add(o);\n" +
                    "            }\n" +
                    "        }\n" +
                    "\n" +
                    "        @Override\n" +
                    "        public Object apply(Object o) {\n" +
                    "            " + constructor + " f = new " + constructor + "(this);\n" +
                    "            f.args.add(o);\n" +
                    "            return f;\n" +
                    "        }\n" +
                    "    }\n";
        }
        return result;
    }
}
