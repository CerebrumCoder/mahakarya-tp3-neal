package Models;

import java.lang.reflect.Field;

public class ProductValidator {
    public static void validate(Object obj) throws IllegalArgumentException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ProductValidation.class)) {
                ProductValidation validation = field.getAnnotation(ProductValidation.class);
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);

                    // Validasi untuk tipe data String
                    if (value instanceof String strValue) {
                        if (strValue.isEmpty()) {
                            throw new IllegalArgumentException(validation.message());
                        }
                    }

                    // Validasi untuk tipe data Integer atau Long
                    if (value instanceof Number numValue) {
                        if (!validation.allowNegative() && numValue.longValue() < 0) {
                            throw new IllegalArgumentException(validation.message());
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field: " + field.getName(), e);
                }
            }
        }
    }
}
