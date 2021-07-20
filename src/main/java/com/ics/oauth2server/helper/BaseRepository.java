package com.ics.oauth2server.helper;


import java.io.Serializable;
import java.util.List;

public interface BaseRepository<E> {

    /**
     * @param entity: entity to save
     * @return Identifier of saved entity
     */
    Serializable save(E entity);

    /**
     * @param entity: entity to save or update
     */
    public void saveOrUpdate(E entity);

    /**
     * @param entity: entity to delete
     */
    boolean deleteById(Class<?> type, Serializable id);

    /**
     * Delete all records
     */
    void deleteAll();

    /**
     * Find all records
     *
     * @return
     */
    List<E> findAll();

    /**
     * Find all records checking whose isFlag is not 0.
     *
     * @return
     */
    List<E> findAllWithIsFlagCheck();

    /**
     * Find all records matching provided entity
     *
     * @param entity: entity object used for search
     * @return
     */
    List<E> findAllByExample(E entity);

    /**
     * Find by primary key
     *
     * @param id
     * @return unique entity
     */
    E findById(Serializable id);

    List<E> findAllWithLike(List<String> Keys, String Value);

    List<E> find(List<String> Keys, List<String> Values, long fromDate, long toDate);

    /**
     * Clear session
     */
    void clear();

    /**
     * Flush session
     */
    void flush();

    /**
     * Close session
     */
    void close();

    /**
     * Rollback transaction
     */
    void rollback();

    // public List<E> findByDate(long fromDate, long toDate);

    public void merge(E entity);

    void evict(E entity);


}

