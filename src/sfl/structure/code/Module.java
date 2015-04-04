package sfl.structure.code;

import sfl.structure.Qualifier;

import java.io.File;
import java.util.ArrayList;

public class Module {
    private Qualifier qualifier;
    private String value;

    public Module(Qualifier qualifier, String value) {
        this.qualifier = qualifier;
        this.value = value;
    }

    public Module(Qualifier qualifier) {
        if (qualifier.isEmpty()) {
            this.qualifier = new Qualifier(new ArrayList<>());
            value = "";
        } else {
            this.qualifier = qualifier.head();
            value = qualifier.tail();
        }
    }

    public String getValue() {
        return value;
    }

    public boolean isEmpty() {
        return qualifier.isEmpty() && value.equals("");
    }

    public boolean hasEmptyPackage() {
        return qualifier.isEmpty();
    }

    public String getPackage() {
        return qualifier.getPackage();
    }

    public String getClassName() {
        return qualifier.getPackage() + "." + value;
    }

    public File remove(File file) {
        for (int i = 0; i < size(); i++) {
            file = file.getParentFile();
        }
        return file;
    }

    public File append(File file) {
        return new File(file.getPath() + qualifier.getPath() + "/" + value + ".sfl");
    }

    private int size() {
        return isEmpty() ? 0 : qualifier.size() + 1;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Module && qualifier.equals(((Module) o).qualifier) && value.equals(((Module) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode() + qualifier.hashCode();
    }

    @Override
    public String toString() {
        return qualifier + value;
    }
}
