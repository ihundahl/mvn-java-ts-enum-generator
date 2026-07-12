package com.example;

public class TypeScriptWriter {

    private static final String INDENT = "    ";

    private final StringBuilder builder = new StringBuilder();
    private int indent = 0;

    public void indent() {
        indent++;
    }

    public void outdent() {
        if (indent > 0) {
            indent--;
        }
    }

    public void line() {
        builder.append('\n');
    }

    public void line(String text) {
        for (int i = 0; i < indent; i++) {
            builder.append(INDENT);
        }
        builder.append(text).append('\n');
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}