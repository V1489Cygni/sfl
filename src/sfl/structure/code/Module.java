package sfl.structure.code;

import sfl.structure.Qualifier;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private Qualifier qualifier;
    private String value;

    public Module(Qualifier qualifier, String value) {
        this.qualifier = qualifier;
        this.value = value;
    }

    public Module(Qualifier qualifier) {
        List<String> qualifiers = qualifier.getQualifiers();
        if (qualifiers.isEmpty()) {
            this.qualifier = new Qualifier(new ArrayList<>());
            value = "";
        } else {
            this.qualifier = new Qualifier(qualifiers.subList(0, qualifiers.size() - 1));
            value = qualifiers.get(qualifiers.size() - 1);
        }
    }

    public String getValue() {
        return value;
    }

    public Qualifier getQualifier() {
        return qualifier;
    }

    public boolean isEmpty() {
        return qualifier.isEmpty() && value.equals("");
    }

    public String getFile() {
        return qualifier.getPath() + "/" + value + ".sfl";
    }

    @Override
    public String toString() {
        return qualifier + value;
    }
}
