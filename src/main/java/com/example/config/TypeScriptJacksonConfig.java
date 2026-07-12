package com.example.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class TypeScriptJacksonConfig {
    public static void configure(ObjectMapper mapper) {
        mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public JsonFormat.Value findFormat(Annotated ann) {
                // Force target package enums to serialize as JSON objects
                if (ann.getRawType() != null && ann.getRawType().isEnum()) {
                    if (ann.getRawType().getName().startsWith("com.yourpackage")) {
                        return JsonFormat.Value.forShape(JsonFormat.Shape.OBJECT);
                    }
                }
                return super.findFormat(ann);
            }

            @Override
            public PropertyName findNameForSerialization(Annotated a) {
                // 2. Intercept the native Java Enum name() method
                if (a instanceof AnnotatedMethod) {
                    AnnotatedMethod method = (AnnotatedMethod) a;
                    if (method.getDeclaringClass().isEnum() &&
                            method.getDeclaringClass().getName().startsWith("com.yourpackage") &&
                            "name".equals(method.getName())) {

                        // Forces Jackson to treat name() like a regular getter mapped to the property
                        // "name"
                        return PropertyName.construct("name");
                    }
                }
                return super.findNameForSerialization(a);
            }
        });
    }
}
