package sfl.structure.code;

import java.util.List;

public class Header extends Statement {
    private String module;
    private List<String> imports;

    public Header(String module, List<String> imports) {
        this.module = module;
        this.imports = imports;
    }

    public String getModule() {
        return module;
    }

    public List<String> getImports() {
        return imports;
    }
}
