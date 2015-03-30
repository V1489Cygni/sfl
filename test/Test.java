import java.util.ArrayList;
import java.util.List;

public class Test {
    public static class next implements Function {
        public List<Object> args = new ArrayList<>();

        public next() {
        }

        public next(next f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 0) {
                next f = new next(this);
                f.args.add(o);
                return f;
            }
            if (true && o instanceof Cons) {
                return ((Cons) o).args.get(1);
            }
            throw new AssertionError();
        }
    }

    public static class top implements Function {
        public List<Object> args = new ArrayList<>();

        public top() {
        }

        public top(top f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 0) {
                top f = new top(this);
                f.args.add(o);
                return f;
            }
            if (true && o instanceof Cons) {
                return ((Cons) o).args.get(0);
            }
            throw new AssertionError();
        }
    }

    public static class sum implements Function {
        public List<Object> args = new ArrayList<>();

        public sum() {
        }

        public sum(sum f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 0) {
                sum f = new sum(this);
                f.args.add(o);
                return f;
            }
            if (true && o instanceof Empty) {
                return 0;
            }
            if (true && o instanceof Cons) {
                return ((Function) ((Function) new Int.add()).apply(((Cons) o).args.get(0))).apply(((Function) new sum()).apply(((Cons) o).args.get(1)));
            }
            throw new AssertionError();
        }
    }

    public static Object main = ((Function) new sum()).apply(((Function) ((Function) new put()).apply(5)).apply(((Function) new next()).apply(((Function) ((Function) new put()).apply(7)).apply(((Function) ((Function) new put()).apply(3)).apply(new Empty())))));

    public static class put implements Function {
        public List<Object> args = new ArrayList<>();

        public put() {
        }

        public put(put f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            if (args.size() < 1) {
                put f = new put(this);
                f.args.add(o);
                return f;
            }
            if (true) {
                return ((Function) ((Function) new Cons()).apply(args.get(0))).apply(o);
            }
            throw new AssertionError();
        }
    }

    public static class Empty implements Function {
        public List<Object> args = new ArrayList<>();

        public Empty() {
        }

        public Empty(Empty f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            Empty f = new Empty(this);
            f.args.add(o);
            return f;
        }
    }
    public static class Cons implements Function {
        public List<Object> args = new ArrayList<>();

        public Cons() {
        }

        public Cons(Cons f) {
            for (Object o : f.args) {
                args.add(o);
            }
        }

        @Override
        public Object apply(Object o) {
            Cons f = new Cons(this);
            f.args.add(o);
            return f;
        }
    }
}