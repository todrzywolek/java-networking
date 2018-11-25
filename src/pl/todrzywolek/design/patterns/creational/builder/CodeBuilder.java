package pl.todrzywolek.design.patterns.creational.builder;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SimpleClass {
    public String className;
    public List<Pair<String, String>> fields = new ArrayList<>();
    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();

    public SimpleClass(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append("public class ")
                .append(className)
                .append(String.format("%s%s%s", newLine, "{", newLine));

        for (Pair<String, String> field : fields) {
            sb
                    .append(String.join("", Collections.nCopies(indentSize, " ")))
                    .append(String.join(" ", "public", field.getValue(), field.getKey()))
                    .append(String.format("%s%s", ";", newLine));
        }

        sb.append(String.format("%s%s", "}", newLine));

        return sb.toString();
    }
}

class CodeBuilder {

    private SimpleClass aClass;

    public CodeBuilder(String className) {
        aClass = new SimpleClass(className);
    }

    public CodeBuilder addField(String name, String type) {
        aClass.fields.add(new Pair<>(name, type));
        return this;
    }

    @Override
    public String toString() {
        return aClass.toString();
    }
}

class CodeBuilderDemo {

    public static void main(String[] args) {
        CodeBuilder cb = new CodeBuilder("Foo");
        System.out.println(cb);
    }
}
