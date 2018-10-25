package com.giantnodes.forum.api;

import java.util.List;

public interface API<S, T> {

    S delete(String id);

    S update(String id, T input);

    S get(String id);

    List<S> all();
}
