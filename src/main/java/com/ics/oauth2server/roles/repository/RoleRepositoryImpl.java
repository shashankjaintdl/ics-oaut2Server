package com.ics.oauth2server.roles.repository;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.BaseRepositoryImpl;
import com.ics.oauth2server.helper.HelperExtension;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<Roles> implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;
    List<Predicate> predicateList;
    HelperExtension helperExtension = new HelperExtension();

    @Override
    public List<Roles> get(Long id, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);
        predicateList = new ArrayList<>();
        if(id>0){
            predicateList.add(criteriaBuilder.equal(root.get("id"),id));
        }
        if(!helperExtension.isNullOrEmpty(name)){
            predicateList.add(criteriaBuilder.equal(root.get("name"),name));
        }
        criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Boolean isExist(Long id,String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);
        List<Predicate> predicateList = new ArrayList<>();
        if(!helperExtension.isNullOrEmpty(name)){
            predicateList.add(criteriaBuilder.equal(root.get("name"),name));
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            if (entityManager.createQuery(criteriaQuery).getResultList().size() > 0) {
                return true;
            }
        }
        return false;
    }




}
