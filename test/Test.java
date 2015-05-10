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
public Object _apply_(Object o) {
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
public static class isPrimeImpl implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public isPrimeImpl() {
}
public isPrimeImpl(Object arg0) {
args.add(arg0);
}
public isPrimeImpl(isPrimeImpl f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object _apply_(Object o) {
if (args.size() < 1) {
isPrimeImpl f = new isPrimeImpl(this);
f.args.add(o);
return f;
}
if (true && ((int) o) == 1) {
return false;
}
if (true) {
return ((boolean) (((int) ((int) args.get(0)) % ((int) o)) == ((int) 0))) || ((boolean) isPrimeImpl(args.get(0), ((int) o) - ((int) 1)));
}
throw new AssertionError();
}
}
public static Object isPrimeImpl(Object arg0, Object arg1) {
if (true && ((int) arg1) == 1) {
return false;
}
if (true) {
return ((boolean) (((int) ((int) arg0) % ((int) arg1)) == ((int) 0))) || ((boolean) isPrimeImpl(arg0, ((int) arg1) - ((int) 1)));
}
throw new AssertionError();
}
public static class tmp implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public tmp() {
}
public tmp(tmp f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object _apply_(Object o) {
if (args.size() < 0) {
tmp f = new tmp(this);
f.args.add(o);
return f;
}
if (true) {
return ((int) 5) + ((int) fib(o));
}
throw new AssertionError();
}
}
public static Object tmp(Object arg0) {
if (true) {
return ((int) 5) + ((int) fib(arg0));
}
throw new AssertionError();
}
public static Object fl() {
if (true) {
return new FCons(new fib(), new FCons(new tmp(), new FEmpty()));
}
throw new AssertionError();
}
public static Object main() {
if (true) {
return ((String) "Result: ") + ((String) str(nList()));
}
throw new AssertionError();
}
public static Object list() {
if (true) {
return map(new flip(new div(), 10), put(fib(8), put(39, put(50, new Empty()))));
}
throw new AssertionError();
}
public static class flip implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public flip() {
}
public flip(Object arg0) {
args.add(arg0);
}
public flip(Object arg0, Object arg1) {
args.add(arg0);
args.add(arg1);
}
public flip(flip f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object _apply_(Object o) {
if (args.size() < 2) {
flip f = new flip(this);
f.args.add(o);
return f;
}
if (true) {
return ((Function) ((Function) args.get(0))._apply_(o))._apply_(args.get(1));
}
throw new AssertionError();
}
}
public static Object flip(Object arg0, Object arg1, Object arg2) {
if (true) {
return ((Function) ((Function) arg0)._apply_(arg2))._apply_(arg1);
}
throw new AssertionError();
}
public static class isPrime implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public isPrime() {
}
public isPrime(isPrime f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object _apply_(Object o) {
if (args.size() < 0) {
isPrime f = new isPrime(this);
f.args.add(o);
return f;
}
if (true) {
return !((boolean) isPrimeImpl(o, ((int) o) - ((int) 1)));
}
throw new AssertionError();
}
}
public static Object isPrime(Object arg0) {
if (true) {
return !((boolean) isPrimeImpl(arg0, ((int) arg0) - ((int) 1)));
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
public Object _apply_(Object o) {
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
public static Object nList() {
if (true) {
return apply(fl(), list());
}
throw new AssertionError();
}
public static void main(String[] args) {
System.out.println(main());
}
}