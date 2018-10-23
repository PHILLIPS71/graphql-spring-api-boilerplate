package com.giantnodes.forum.utility;

import java.lang.reflect.Field;

public class UtilReflection<T> {

    public T merge(T current, T target) {
        try {
            for (Field field : current.getClass().getDeclaredFields()) {
                Field f = target.getClass().getDeclaredField(field.getName());

                if (field.get(current) != f.get(target)) {
                    field.set(current, f.get(target));
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return current;
    }
}
