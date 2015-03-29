import java.util.ArrayList;
import java.util.List;

public class Int {
    public static class Mul implements Function {
        public List<Object> args = new ArrayList<>();

        public Mul() {
        }

        public Mul(Mul f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 1) {
                Mul mul = new Mul(this);
                mul.args.add(o);
                return mul;
            }
            return ((int) args.get(0)) * ((int) o);
        }
    }

    public static class Add implements Function {
        public List<Object> args = new ArrayList<>();

        public Add() {
        }

        public Add(Add f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 1) {
                Add add = new Add(this);
                add.args.add(o);
                return add;
            }
            return ((int) args.get(0)) + ((int) o);
        }
    }
}