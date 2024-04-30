package orlov.oleksandr.programming.citycountryspringrest.service.interfaces;

import java.util.List;

public interface CrudService<T, ID>{
    List<T> findAll();
    T findById(ID id);
    T create(T entity);
    T update(T entity);
    void delete(T entity);
}