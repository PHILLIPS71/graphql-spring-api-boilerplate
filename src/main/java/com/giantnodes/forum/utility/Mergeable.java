package com.giantnodes.forum.utility;

public interface Mergeable<T, V> {

    T merge(V target);
}
