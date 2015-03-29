import java.util.ArrayList;
import java.util.List;

public class Test {
	public static class X implements Function {
		public List<Object> args = new ArrayList<>();

		public X() {
		}

		public X(X f) {
			for (Object o : f.args) {
				args.add(o);
			}
		}

		@Override
		public Object apply(Object o) {
			if (args.size() < 0) {
				X f = new X(this);
				f.args.add(o);
				return f;
			}
			if (true) {
				return ((Function) o).apply(3);
			}
			throw new AssertionError();
		}
	}

	public static class Y implements Function {
		public List<Object> args = new ArrayList<>();

		public Y() {
		}

		public Y(Y f) {
			for (Object o : f.args) {
				args.add(o);
			}
		}

		@Override
		public Object apply(Object o) {
			if (args.size() < 0) {
				Y f = new Y(this);
				f.args.add(o);
				return f;
			}
			if (true) {
				return ((Function) new X()).apply(((Function) o).apply(5));
			}
			throw new AssertionError();
		}
	}

	public static class Sum implements Function {
		public List<Object> args = new ArrayList<>();

		public Sum() {
		}

		public Sum(Sum f) {
			for (Object o : f.args) {
				args.add(o);
			}
		}

		@Override
		public Object apply(Object o) {
			if (args.size() < 2) {
				Sum f = new Sum(this);
				f.args.add(o);
				return f;
			}
			if (true) {
				return ((Function) ((Function) new Int.Add()).apply(args.get(0))).apply(((Function) ((Function) new Int.Add()).apply(args.get(1))).apply(o));
			}
			throw new AssertionError();
		}
	}

	public static class Z implements Function {
		public List<Object> args = new ArrayList<>();

		public Z() {
		}

		public Z(Z f) {
			for (Object o : f.args) {
				args.add(o);
			}
		}

		@Override
		public Object apply(Object o) {
			if (args.size() < -1) {
				Z f = new Z(this);
				f.args.add(o);
				return f;
			}
			if (true) {
				return ((Function) new Y()).apply(((Function) new Sum()).apply(20));
			}
			throw new AssertionError();
		}
	}
}