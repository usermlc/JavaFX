package com.await.repository;

import java.util.Optional;
import java.util.Set;

public interface Repository<T> {
    Optional<T> findById(int id);
    Set<T> findAll();
    boolean add(T entity);
    boolean remove(int id);
}
