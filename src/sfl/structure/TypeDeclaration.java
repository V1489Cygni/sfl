package sfl.structure;

import sfl.structure.code.expression.Constructor;
import sfl.structure.type.Type;
import sfl.structure.type.TypeIdentifier;

import java.util.List;

public class TypeDeclaration implements Statement {
    private TypeIdentifier name;
    private List<Constructor> constructors;
    private List<List<Type>> arguments;

    public TypeDeclaration(TypeIdentifier name, List<Constructor> constructors, List<List<Type>> arguments) {
        this.name = name;
        this.constructors = constructors;
        this.arguments = arguments;
    }

    public TypeIdentifier getName() {
        return name;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    public List<List<Type>> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        String result = name + " = ";
        boolean first = true;
        for (int i = 0; i < constructors.size(); i++) {
            result += (first ? "" : " | ") + constructors.get(i);
            for (int j = 0; j < arguments.get(i).size(); j++) {
                result += " " + arguments.get(i).get(j);
            }
        }
        return result;
    }
}
