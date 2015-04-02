package sfl.structure;

import java.util.List;

public class Qualifier {
    private List<String> qualifiers;

    public Qualifier(List<String> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public List<String> getQualifiers() {
        return qualifiers;
    }

    public int getSize() {
        return qualifiers.size();
    }

    public boolean isEmpty() {
        return qualifiers.isEmpty();
    }

    public String getPath() {
        String result = "";
        for (int i = 0; i < qualifiers.size(); i++) {
            result += "/" + qualifiers.get(i).toLowerCase();
        }
        return result;
    }

    public String getPackage() {
        String result = qualifiers.get(0);
        for (int i = 1; i < qualifiers.size(); i++) {
            result += "." + qualifiers.get(i).toLowerCase();
        }
        return result;
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
