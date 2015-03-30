package sfl.structure.type;

public class TypeIdentifier implements Type {
    private String name;

    public TypeIdentifier(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof TypeIdentifier && name.equals(((TypeIdentifier) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
