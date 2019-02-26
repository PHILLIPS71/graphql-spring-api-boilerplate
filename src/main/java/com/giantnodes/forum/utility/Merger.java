package com.giantnodes.forum.utility;

import java.lang.reflect.Field;

public class Merger<T, I> {

    public T merge(T type, I target) {
        try {
            for (Field field : target.getClass().getDeclaredFields()) {
                Field f = type.getClass().getDeclaredField(field.getName());
                field.setAccessible(true);
                f.setAccessible(true);


                if (field.get(target) != null) {
                    if (f.get(type) != field.get(target)) {
                        f.set(type, field.get(target));
                    }
                }

                field.setAccessible(false);
                f.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return type;
    }

}
