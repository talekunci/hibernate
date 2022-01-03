package ua.goit.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();
    Optional<T> get(Long id);
    void create(T newEntity);
    void update(T entity);
    void delete(T entity);

}
