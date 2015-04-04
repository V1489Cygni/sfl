/*Auto-generated code*/
package data;
import sfl.util.Function;
public class List {
public static class Empty implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public Empty() {
}
public Empty(Empty f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
Empty f = new Empty(this);
f.args.add(o);
return f;
}
}
public static class Cons implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
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
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
Cons f = new Cons(this);
f.args.add(o);
return f;
}
}
public static class str implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public str() {
}
public str(str f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 0) {
str f = new str(this);
f.args.add(o);
return f;
}
if (true) {
return ((String) "<") + ((String) strImpl(o, false));
}
throw new AssertionError();
}
}
public static Object str(Object arg0) {
if (true) {
return ((String) "<") + ((String) strImpl(arg0, false));
}
throw new AssertionError();
}
public static class top implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public top() {
}
public top(top f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
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
public java.util.List<Object> args = new java.util.ArrayList<>();
public get() {
}
public get(Object arg0) {
args.add(arg0);
}
public get(get f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
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
public static class strImpl implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public strImpl() {
}
public strImpl(Object arg0) {
args.add(arg0);
}
public strImpl(strImpl f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 1) {
strImpl f = new strImpl(this);
f.args.add(o);
return f;
}
if (true && args.get(0) instanceof Empty) {
return ">";
}
if (true && args.get(0) instanceof Cons && ((boolean) o) == true) {
return ((String) ((String) ", ") + ((int) ((Cons) args.get(0)).args.get(0))) + ((String) strImpl(((Cons) args.get(0)).args.get(1), true));
}
if (true && args.get(0) instanceof Cons && ((boolean) o) == false) {
return ((int) ((Cons) args.get(0)).args.get(0)) + ((String) strImpl(((Cons) args.get(0)).args.get(1), true));
}
throw new AssertionError();
}
}
public static Object strImpl(Object arg0, Object arg1) {
if (true && arg0 instanceof Empty) {
return ">";
}
if (true && arg0 instanceof Cons && ((boolean) arg1) == true) {
return ((String) ((String) ", ") + ((int) ((Cons) arg0).args.get(0))) + ((String) strImpl(((Cons) arg0).args.get(1), true));
}
if (true && arg0 instanceof Cons && ((boolean) arg1) == false) {
return ((int) ((Cons) arg0).args.get(0)) + ((String) strImpl(((Cons) arg0).args.get(1), true));
}
throw new AssertionError();
}
public static class map implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public map() {
}
public map(Object arg0) {
args.add(arg0);
}
public map(map f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
}
public Object apply(Object o) {
if (args.size() < 1) {
map f = new map(this);
f.args.add(o);
return f;
}
if (true && o instanceof Empty) {
return new Empty();
}
if (true && o instanceof Cons) {
return new Cons(((Function) args.get(0)).apply(((Cons) o).args.get(0)), map(args.get(0), ((Cons) o).args.get(1)));
}
throw new AssertionError();
}
}
public static Object map(Object arg0, Object arg1) {
if (true && arg1 instanceof Empty) {
return new Empty();
}
if (true && arg1 instanceof Cons) {
return new Cons(((Function) arg0).apply(((Cons) arg1).args.get(0)), map(arg0, ((Cons) arg1).args.get(1)));
}
throw new AssertionError();
}
public static class put implements Function {
public java.util.List<Object> args = new java.util.ArrayList<>();
public put() {
}
public put(Object arg0) {
args.add(arg0);
}
public put(put f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
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
public java.util.List<Object> args = new java.util.ArrayList<>();
public remove() {
}
public remove(remove f) {
args.addAll(f.args.stream().collect(java.util.stream.Collectors.toList()));
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
}