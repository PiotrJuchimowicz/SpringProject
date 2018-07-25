package comcompany.app.base.Services;

import java.util.List;

public interface GenericService<T> {

    List<T> getAll();
    T create(T object);
    T read(Long id);
    T update(T object);
    void delete(Long id);
    void deleteAll();

}
