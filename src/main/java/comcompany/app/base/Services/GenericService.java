package comcompany.app.base.Services;

import java.lang.reflect.Field;
import java.util.List;

public interface GenericService<T> {

    List<T> getAll();

    T create(T object);

    T read(Long id);

    T update(T object);

    void delete(Long id);

    void deleteAll();

    Field[] getAllFields(Class<T> c);

}
