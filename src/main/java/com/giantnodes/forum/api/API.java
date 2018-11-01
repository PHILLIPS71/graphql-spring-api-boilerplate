package com.giantnodes.forum.api;

import java.util.List;

public interface API<T, I> {

    T create(T object);

    T delete(String id);

    T update(String id, I input);

    T get(String id);

    List<T> all();
}
