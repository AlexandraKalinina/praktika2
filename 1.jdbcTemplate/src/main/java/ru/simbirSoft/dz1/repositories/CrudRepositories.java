package ru.simbirSoft.dz1.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepositories<T, ID> {
    Optional<T> find(ID id);
    void save(T object);
    void delete(ID id);
    List<T> findAll();
}
