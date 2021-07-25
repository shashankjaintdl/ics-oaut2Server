package com.ics.oauth2server.helper;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public abstract class BaseRepositoryImpl<E> implements BaseRepository<E> {

    private final Class<E> entityClass;

    public Session session;

    @PersistenceContext
    private EntityManager entityManger;

    public BaseRepositoryImpl() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public Session getSession() {
        session = this.entityManger.unwrap(Session.class);
        if (session.isOpen() || session.getTransaction().isActive()) {
            return session;
        }
        session.beginTransaction();
        return session;
    }

    @Override
    public Serializable save(E entity) {
        session = getSession();
        Serializable s = session.save(entity);
        return s;
    }

    @Override
    public void saveOrUpdate(E entity) {
        session = getSession();
        session.saveOrUpdate(entity);
    }


    @Override
    public void merge(E entity) {
        session = getSession();
        session.merge(entity);
    }

    @Override
    public boolean deleteById(Class<?> type, Serializable id) {
        session = getSession();
        Object persistentInstance = session.load(type, id);
        if (persistentInstance != null) {
            session.delete(persistentInstance);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAll() {
        session = getSession();
        List<E> entities = findAll();
        for (E entity : entities) {
            session.delete(entity);
        }
    }

    @Override
    public List<E> findAll() {
        session = getSession();
        return session.createCriteria(this.entityClass).list();
    }

    @Override
    public List<E> findAllWithIsFlagCheck() {
        session = getSession();
        Criteria criteria = session.createCriteria(this.entityClass);
        criteria.add(Restrictions.eq("isFlag", true));
        return criteria.list();
    }

    @Override
    public List<E> findAllByExample(E entity) {
        Example example = Example.create(entity).ignoreCase().enableLike().excludeZeroes();
        return session.createCriteria(this.entityClass).add(example).list();
    }

    @Override
    public E findById(Serializable id) {
        session = getSession();
        return session.get(this.entityClass, id);
    }

    @Override
    public List<E> findAllWithLike(List<String> keys, String searchValue) {
        Disjunction disjunction = Restrictions.disjunction();
        Criterion criterion;
        for (int i = 0; i < keys.size(); i++) {
            criterion = Restrictions.ilike(keys.get(i), searchValue, MatchMode.START);
            disjunction.add(criterion);
        }
        Criteria crit = getSession().createCriteria(this.entityClass);
        crit.add(disjunction);
        List<E> results = crit.list();
        return results;
    }

    @Override
    public void clear() {
        session.clear();
    }

    @Override
    public void flush() {
        if (session.getTransaction().isActive() || session.isOpen() ) {
            session.flush();
        }
    }

    @Override
    public void close() {
        if (session.getTransaction().isActive() || session.isOpen()) {
            session.getTransaction().commit();
        }
    }

    @Override
    public void rollback() {
        if (session.getTransaction().isActive() || session.isOpen()) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void evict(E entity) {
        session = getSession();
        session.evict(entity);
    }

    @Override
    public List<E> find(List<String> keys, List<String> values, long fromDate, long toDate) {
        HelperExtension helperExtension = new HelperExtension();
        Criteria criteria = getSession().createCriteria(this.entityClass);
        for (int i = 0; i < keys.size(); i++) {
            if (!helperExtension.isNullOrEmpty(values.get(i))) {
                criteria.add(Restrictions.eq(keys.get(i), values.get(i)));
            }
        }
        if (fromDate != 0 && toDate != 0) {
            Date from = helperExtension.timestampToDate(fromDate);
            Date to = helperExtension.timestampToDatePlusOneDay(toDate);
            criteria.add(Restrictions.between("dateTime", from, to));
        }
        criteria.add(Restrictions.eq("isFlag", 1));
        List<E> results = criteria.list();
        return results;
    }

}
