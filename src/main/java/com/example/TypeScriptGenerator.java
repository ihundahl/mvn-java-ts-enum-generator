package com.example;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

public class TypeScriptGenerator {
    public static void main(String[] args) throws Exception {

        String outputDir = System.getProperty("ts.outputDir");
        String scanPackages = System.getProperty("ts.scanPackages");

        if (outputDir == null || outputDir.isBlank()) {
            throw new IllegalStateException(
                    "Missing system property: ts.outputDir");
        }

        if (scanPackages == null || scanPackages.isBlank()) {
            throw new IllegalStateException(
                    "Missing system property: ts.scanPackages");
        }

        Path outputPath = Paths.get(outputDir);

        Files.createDirectories(outputPath);

        EnumGenerator generator = new EnumGenerator();

        String[] packages = System.getProperty("ts.scanPackages")
                .split("\\s*,\\s*");

        try (ScanResult scan = new ClassGraph()
                .acceptPackages(packages)
                .enableClassInfo()
                .scan();) {

            for (ClassInfo classInfo : scan.getAllClasses()) {

                Class<?> clazz = classInfo.loadClass();

                if (!clazz.isEnum()) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) clazz;

                String ts = generator.generate(enumClass);

                // Path packageDir = outputPath.resolve(
                // enumClass.getPackageName()
                // .replace(scanPackages + ".", "")
                // .replace('.', '/'));

                Files.createDirectories(outputPath);

                Path file = outputPath.resolve(
                        enumClass.getSimpleName() + ".ts");

                Files.writeString(file, ts, StandardCharsets.UTF_8);

                System.out.println("Generated " + file);
            }
        }
    }
}
