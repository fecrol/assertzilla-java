package com.github.fecrol.assertzilla.core.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectionHelpers {

    public static Method getMethod(Class<?> parentClass, String methodName, Class<?>... parameterTypes) {
        try {
            return parentClass.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Field> getDeclaredFields(Class<?> parentClass) {
        return List.of(parentClass.getDeclaredFields());
    }

    public static <T> T getAnnotationValue(Class<? extends Annotation> annotation, Method method) {
        if (method.isAnnotationPresent(annotation)) {
            try {
                Annotation annotationInstance = method.getAnnotation(annotation);
                Method getValue = getMethod(annotation, "value");
                Class<?> returnType = getValue.getReturnType();
                return (T) returnType.cast(getValue.invoke(annotationInstance));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else return null;
    }
}
