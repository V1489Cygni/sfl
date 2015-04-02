/*Auto-generated code*/
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import sfl.util.Function;
public class Test {
public static class Empty implements Function {
public List<Object> args = new ArrayList<>();
public Empty() {
}
public Empty(Empty f) {
args.addAll(f.args.stream().collect(Collectors.toList()));
}
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
public Cons(Object arg0) {
args.add(arg0);
}
public Cons(Object arg0, Object arg1) {
args.add(arg0);
args.add(arg1);
}
public Cons(Cons f) {
args.addAll(f.args.stream().collect(Collectors.toList()));
}
public Object apply(Object o) {
Cons f = new Cons(this);
f.args.add(o);
return f;
}
}
public static class top implements Function {
public static List<Object> args = new ArrayList<>();
public top(Object arg0) {
args.add(arg0);
}
public top(top f) {
args.addAll(f.args.stream().collect(Collectors.toList()));
}
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
public static Object top(Object arg0) {
if (true && arg0 instanceof Cons) {
return ((Cons) arg0).args.get(0);
}
throw new AssertionError();
}
public static class get implements Function {
public static List<Object> args = new ArrayList<>();
public get(Object arg0) {
args.add(arg0);
}
public get(Object arg0, Object arg1) {
args.add(arg0);
args.add(arg1);
}
public get(get f) {
args.addAll(f.args.stream().collect(Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 1) {
get f = new get(this);
f.args.add(o);
return f;
}
if (true && args.get(0) instanceof Cons && ((int) o) == 0) {
return ((Cons) args.get(0)).args.get(0);
}
if (true && args.get(0) instanceof Cons) {
return get(((Cons) args.get(0)).args.get(1), ((int) o) - ((int) 1));
}
throw new AssertionError();
}
}
public static Object get(Object arg0, Object arg1) {
if (true && arg0 instanceof Cons && ((int) arg1) == 0) {
return ((Cons) arg0).args.get(0);
}
if (true && arg0 instanceof Cons) {
return get(((Cons) arg0).args.get(1), ((int) arg1) - ((int) 1));
}
throw new AssertionError();
}
 public static Object main = get(put(3, remove(put(2, put(7, put(1, new Empty()))))), 1);
public static class put implements Function {
public static List<Object> args = new ArrayList<>();
public put(Object arg0) {
args.add(arg0);
}
public put(Object arg0, Object arg1) {
args.add(arg0);
args.add(arg1);
}
public put(put f) {
args.addAll(f.args.stream().collect(Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 1) {
put f = new put(this);
f.args.add(o);
return f;
}
if (true) {
return new Cons(args.get(0), o);
}
throw new AssertionError();
}
}
public static Object put(Object arg0, Object arg1) {
if (true) {
return new Cons(arg0, arg1);
}
throw new AssertionError();
}
public static class remove implements Function {
public static List<Object> args = new ArrayList<>();
public remove(Object arg0) {
args.add(arg0);
}
public remove(remove f) {
args.addAll(f.args.stream().collect(Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 0) {
remove f = new remove(this);
f.args.add(o);
return f;
}
if (true && o instanceof Cons) {
return ((Cons) o).args.get(1);
}
throw new AssertionError();
}
}
public static Object remove(Object arg0) {
if (true && arg0 instanceof Cons) {
return ((Cons) arg0).args.get(1);
}
throw new AssertionError();
}

 public static void main(String[] args) {
 System.out.println(main);
 }
}