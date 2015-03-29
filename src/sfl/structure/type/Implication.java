package sfl.structure.type;

public class Implication implements Type {
    private Type left, right;

    public Implication(Type left, Type right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left + " -> " + right + ")";
    }
}
