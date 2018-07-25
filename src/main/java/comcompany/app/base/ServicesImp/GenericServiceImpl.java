package comcompany.app.base.ServicesImp;

import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public abstract class GenericServiceImpl<T> implements GenericService<T> {


    private GenericRepository<T> genericRepository;
    private Logger log = LoggerFactory.getLogger(getClass().getName());


    public GenericServiceImpl() {
    }


    @Override
    public List<T> getAll() {
        List<T> result = genericRepository.findAll();
        if (result.isEmpty()) {
            log.error("Unable to get  all objects from DB.Empty query result");
            throw new RuntimeException("Unable to get  all objects from DB.Empty query result");

        } else
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

    //getdeclaredFields zwraca wszystkie pola
//getFIelds zwraca tylko publiczne pola
    @Override
    public T update(T object) {


        Field field = null;
        Field[] fields = object.getClass().getDeclaredFields();

        //Na wszelki wypadek ustawiam dostęp do wszystkich pol
        Arrays.stream(fields).forEach((f -> f.setAccessible(true)));
        String fieldName = fields[0].getName();
        Long fieldIdValue = null;

        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            log.error("Unable to find field : " + fieldName, e);
        }

        try {
            fieldIdValue = (Long) field.get(object);
        } catch (IllegalAccessException e) {
            log.error("Unable to get " + fieldName + " value", e);
        }

        //Ma to sens - wszystko  to co powyżej sie robi aby sprawdzic czy taki obiekt istnieje w bazie
        //bo bez tego czasem by dodalo a nie zaktualizowalo a mam miesc pewnosc co robi program - czy dodaje czy aktualizuje

        object = read(fieldIdValue);

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

    public GenericRepository<T> getGenericRepository() {
        return genericRepository;
    }

    public abstract void setRepository(GenericRepository<T> genericRepository);

    public void setGenericRepository(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }
}
