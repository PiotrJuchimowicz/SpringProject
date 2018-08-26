package comcompany.app.base.ServicesImp;

import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.GenericService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public abstract class GenericServiceImpl<T> implements GenericService<T> {
    private GenericRepository<T> genericRepository;
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Override
    public List<T> getAll() {
        List<T> result = genericRepository.findAll();
        return result;

    }

    @Override
    public T create(T object) {
        return genericRepository.save(object);
    }

    @Override
    public T read(Long id) {
        Optional<T> optional = genericRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        else {
            log.error("Unable to read object from DB");
            throw new RuntimeException("Unable to read object from DB");
        }
    }

    @Override
    public T update(T object) {
        Field field = null;
        /*getdeclaredFields returns all fields
        getFIelds returns only public fields*/
        Field[] fields = object.getClass().getDeclaredFields();
        String fieldName = fields[0].getName();
        Long fieldIdValue = null;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            //setting public id
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            log.error("Unable to find field : " + fieldName, e);
        }
        try {
            fieldIdValue = (Long) field.get(object);
        } catch (IllegalAccessException e) {
            log.error("Unable to get " + fieldName + " value", e);
        } catch (NullPointerException e) {
            log.error("Field " + fieldName + " is null", e);
        }
        /*The above code ensures that this object is persisted in DB.
        The goal of this method is to update(not add) - So we must be sure that every single object will be updated
        * Without him I dont know whether this method adds  or update  */
        return genericRepository.save(object);
    }

    @Override
    public void delete(Long id) {
        genericRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        genericRepository.deleteAll();
    }

    public abstract void setRepository(GenericRepository<T> genericRepository);

    @Override
    public Field[] getAllFields(Class<T> c) {

        return c.getDeclaredFields();
    }
}
