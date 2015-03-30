package Data;

import util.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Int {
    public static class mul implements Function {
        public List<Object> args = new ArrayList<>();

        public mul() {
        }

        public mul(mul f) {
            args.addAll(f.args.stream().collect(Collectors.toList()));
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 1) {
                mul mul = new mul(this);
                mul.args.add(o);
                return mul;
            }
            return ((int) args.get(0)) * ((int) o);
        }
    }

    public static class add implements Function {
        public List<Object> args = new ArrayList<>();

        public add() {
        }

        public add(add f) {
            args.addAll(f.args.stream().collect(Collectors.toList()));
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 1) {
                add add = new add(this);
                add.args.add(o);
                return add;
            }
            return ((int) args.get(0)) + ((int) o);
        }
    }
}