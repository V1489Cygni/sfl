package sfl.structure;

import java.util.List;

public class Qualifier {
    private List<String> qualifiers;

    public Qualifier(List<String> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public int size() {
        return qualifiers.size();
    }

    public boolean isEmpty() {
        return qualifiers.isEmpty();
    }

    public Qualifier head() {
        return new Qualifier(qualifiers.subList(0, qualifiers.size() - 1));
    }

    public String tail() {
        return qualifiers.get(qualifiers.size() - 1);
    }

    public String getPath() {
        String result = "";
        for (int i = 0; i < qualifiers.size(); i++) {
            result += "/" + qualifiers.get(i).toLowerCase();
        }
        return result;
    }

    public String getPackage() {
        String result = qualifiers.get(0).toLowerCase();
        for (int i = 1; i < qualifiers.size(); i++) {
            result += "." + qualifiers.get(i).toLowerCase();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Qualifier && qualifiers.equals(((Qualifier) o).qualifiers);
    }

    @Override
    public int hashCode() {
        return qualifiers.hashCode();
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < qualifiers.size(); i++) {
            result += qualifiers.get(i) + ".";
        }
        return result;
    }
}
