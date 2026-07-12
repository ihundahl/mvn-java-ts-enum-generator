package com.example;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class EnumGenerator {

    private static final Set<String> RESERVED_PROPERTIES = Set.of("name");

    private record Property(
            String name,
            Method getter,
            Class<?> type) {
    }

    public String generate(Class<? extends Enum<?>> enumClass) throws Exception {

        TypeScriptWriter ts = new TypeScriptWriter();

        List<Property> properties = getProperties(enumClass);

        writeUnion(ts, enumClass);
        ts.line();
        writeValues(ts, enumClass, properties);

        return ts.toString();
    }

    private List<Property> getProperties(Class<?> enumClass) {

        return Arrays.stream(enumClass.getMethods())

                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> m.getParameterCount() == 0)
                .filter(m -> m.getName().startsWith("get"))
                .filter(m -> !m.getName().equals("getDeclaringClass"))
                .filter(m -> !m.getName().equals("getClass"))
                .sorted(Comparator.comparing(Method::getName))

                .map(m -> new Property(
                        decapitalize(m.getName().substring(3)),
                        m,
                        m.getReturnType()))
                .filter(p -> !RESERVED_PROPERTIES.contains(p.name()))
                .toList();
    }

    private void writeUnion(
            TypeScriptWriter ts,
            Class<? extends Enum<?>> enumClass) {

        Enum<?>[] values = enumClass.getEnumConstants();

        StringBuilder line = new StringBuilder();
        line.append("export type ")
                .append(enumClass.getSimpleName())
                .append(" = ");

        for (int i = 0; i < values.length; i++) {

            if (i > 0) {
                line.append(" | ");
            }

            line.append("'")
                    .append(values[i].name())
                    .append("'");
        }

        line.append(";");

        ts.line(line.toString());
    }

    private void writeValues(
            TypeScriptWriter ts,
            Class<? extends Enum<?>> enumClass,
            List<Property> properties) throws Exception {

        String enumName = enumClass.getSimpleName();

        ts.line("export const " + enumName + "Values = {");
        ts.indent();

        for (Enum<?> constant : enumClass.getEnumConstants()) {

            ts.line(constant.name() + ": {");
            ts.indent();

            ts.line("name: \"" + constant.name() + "\",");

            for (Property property : properties) {

                Object value = property.getter().invoke(constant);

                ts.line(property.name()
                        + ": "
                        + toTypeScriptLiteral(value)
                        + ",");
            }

            ts.outdent();
            ts.line("},");
        }

        ts.outdent();
        ts.line("} as const;");
    }

    private String decapitalize(String text) {

        if (text.isEmpty()) {
            return text;
        }

        return Character.toLowerCase(text.charAt(0))
                + text.substring(1);
    }

    private String toTypeScriptLiteral(Object value) {

        if (value == null) {
            return "null";
        }

        if (value instanceof String s) {
            return "\"" + escape(s) + "\"";
        }

        if (value instanceof Character c) {
            return "\"" + escape(c.toString()) + "\"";
        }

        if (value instanceof Boolean b) {
            return b.toString();
        }

        if (value instanceof Number n) {
            return n.toString();
        }

        if (value instanceof Enum<?> e) {
            return "\"" + e.name() + "\"";
        }

        throw new IllegalArgumentException(
                "Unsupported value type: " + value.getClass().getName());
    }

    private String escape(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}