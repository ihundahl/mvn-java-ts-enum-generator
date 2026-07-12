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
        Set<Class<? extends Enum<?>>> importedEnums = gatherEnumImports(enumClass, properties);

        writeImports(ts, importedEnums);

        if (!importedEnums.isEmpty()) {
            ts.line();
        }

        writeUnion(ts, enumClass);
        ts.line();
        writeValues(ts, enumClass, properties);

        return ts.toString();
    }

    private Set<Class<? extends Enum<?>>> gatherEnumImports(
            Class<? extends Enum<?>> enumClass,
            List<Property> properties) throws Exception {

        Set<Class<? extends Enum<?>>> imports = new java.util.HashSet<>();

        for (Enum<?> constant : enumClass.getEnumConstants()) {
            for (Property property : properties) {
                Object value = property.getter().invoke(constant);

                if (value == null) {
                    continue;
                }

                // Single enum value
                if (value instanceof Enum<?> e) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) e.getClass();

                    if (!enumType.equals(enumClass)) {
                        imports.add(enumType);
                    }
                    continue;
                }

                // Collections
                if (value instanceof java.util.Collection<?> coll) {
                    for (Object elem : coll) {
                        if (elem instanceof Enum<?> ee) {
                            @SuppressWarnings("unchecked")
                            Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) ee.getClass();

                            if (!enumType.equals(enumClass)) {
                                imports.add(enumType);
                            }
                        }
                    }
                    continue;
                }

                // Arrays (including primitive arrays) - check elements
                if (value.getClass().isArray()) {
                    int len = java.lang.reflect.Array.getLength(value);
                    for (int i = 0; i < len; i++) {
                        Object elem = java.lang.reflect.Array.get(value, i);
                        if (elem instanceof Enum<?> ee) {
                            @SuppressWarnings("unchecked")
                            Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) ee.getClass();

                            if (!enumType.equals(enumClass)) {
                                imports.add(enumType);
                            }
                        }
                    }
                }
            }
        }

        return imports;
    }

    private void writeImports(
            TypeScriptWriter ts,
            Set<Class<? extends Enum<?>>> importedEnums) {

        importedEnums.stream()
                .sorted(Comparator.comparing(Class::getSimpleName))
                .forEach(enumClass -> ts.line("import { "
                        + enumClass.getSimpleName()
                        + "Values } from './"
                        + enumClass.getSimpleName()
                        + "';"));
    }

    private String toTypeScriptLiteral(Object value) {
        return TypeMapper.toTypeScriptLiteral(value);
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

    private String escape(String value) {

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}