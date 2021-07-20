package com.ics.oauth2server.role.repository;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.BaseRepositoryImpl;
import com.ics.oauth2server.helper.ConstantExtension;
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
    ConstantExtension constantExtension = new ConstantExtension();
    HelperExtension helperExtension = new HelperExtension();

    @Override
    public Boolean existByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Roles> criteriaQuery = criteriaBuilder.createQuery(Roles.class);
        Root<Roles> root = criteriaQuery.from(Roles.class);
        List<Predicate> predicateList = new ArrayList<>();
        if(!helperExtension.isNullOrEmpty(name)){
            predicateList.add(criteriaBuilder.equal(root.get("name"),name));
        }
        return null;
    }




}
