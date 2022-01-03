package ua.goit.dao;

import ua.goit.dao.config.PersistenceProvider;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements Dao<T> {

    private final Class<T> type;
    protected EntityManager em = PersistenceProvider.getEntityManager();

    protected AbstractDao(Class<T> type) {
        this.type = type;
    }

    public AbstractDao() {
        Type daoType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) daoType).getActualTypeArguments();
        this.type = ((Class<T>) params[0]);
    }

    @Override
    public List<T> getAll() {
        return em.createQuery("from " + type.getSimpleName(), type).getResultList();
    }

    @Override
    public Optional<T> get(Long id) {
        T entity = em.find(type, id);

        return entity == null ? Optional.empty() : Optional.of(entity);
    }

    @Override
    public void create(T newEntity) {
        if (newEntity == null) return;

        em.getTransaction().begin();
        em.persist(newEntity);
        em.getTransaction().commit();
    }

    @Override
    public void update(T entity) {
        if (entity == null) return;

        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public void delete(T entity) {
        if (entity == null) return;

        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }
}
