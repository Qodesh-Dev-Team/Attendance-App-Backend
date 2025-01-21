package com.qcc.hallow.util;

import java.lang.reflect.Method;

public class ObjectUtil {
    public static boolean isObjectNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        
        // Get all methods of the object's class
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 0 && method.canAccess(obj)) {
                try {
                    // Check if the method corresponds to a record component
                    if (isRecordComponentAccessor(obj, method)) {
                        Object value = method.invoke(obj);
                        if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    // Handle exception (e.g., log it)
                    System.err.println("Reflection error: " + e.getMessage());
                }
            }
        }
        return false;
    }

    private static boolean isRecordComponentAccessor(Object obj, Method method) {
        // Check if the method name corresponds to any of the record's components
        return obj.getClass().isRecord() && 
               java.util.Arrays.stream(obj.getClass().getRecordComponents())
                   .anyMatch(rc -> rc.getName().equals(method.getName()));
    }
}
