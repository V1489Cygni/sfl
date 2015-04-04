/*Auto-generated code*/
import sfl.util.Function;
import static data.List.*;
public class Test {
public static class div implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public div() {
}
public div(Object arg0) {
args.add(arg0);
}
public div(div f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 1) {
div f = new div(this);
f.args.add(o);
return f;
}
if (true) {
return ((int) args.get(0)) / ((int) o);
}
throw new AssertionError();
}
}
public static Object div(Object arg0, Object arg1) {
if (true) {
return ((int) arg0) / ((int) arg1);
}
throw new AssertionError();
}
public static class swap implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public swap() {
}
public swap(Object arg0) {
args.add(arg0);
}
public swap(Object arg0, Object arg1) {
args.add(arg0);
args.add(arg1);
}
public swap(swap f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 2) {
swap f = new swap(this);
f.args.add(o);
return f;
}
if (true) {
return ((Function) ((Function) args.get(0)).apply(o)).apply(args.get(1));
}
throw new AssertionError();
}
}
public static Object swap(Object arg0, Object arg1, Object arg2) {
if (true) {
return ((Function) ((Function) arg0).apply(arg2)).apply(arg1);
}
throw new AssertionError();
}
public static Object main() {
if (true) {
return ((String) "Result: ") + ((String) str(list()));
}
throw new AssertionError();
}
public static Object list() {
if (true) {
return map(new swap(new div(), 10), put(fib(30), put(20, new Empty())));
}
throw new AssertionError();
}
public static class fib implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public fib() {
}
public fib(fib f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 0) {
fib f = new fib(this);
f.args.add(o);
return f;
}
if (true && ((int) o) == 0) {
return 1;
}
if (true && ((int) o) == 1) {
return 1;
}
if (true) {
return ((int) fib(((int) o) - ((int) 1))) + ((int) fib(((int) o) - ((int) 2)));
}
throw new AssertionError();
}
}
public static Object fib(Object arg0) {
if (true && ((int) arg0) == 0) {
return 1;
}
if (true && ((int) arg0) == 1) {
return 1;
}
if (true) {
return ((int) fib(((int) arg0) - ((int) 1))) + ((int) fib(((int) arg0) - ((int) 2)));
}
throw new AssertionError();
}
public static void main(String[] args) {
System.out.println(main());
}
}