package com.ics.oauth2server.user.repository;

import com.ics.oauth2server.common.entities.User;
import com.ics.oauth2server.helper.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User> implements UserRepository{

    @PersistenceContext
    private EntityManager entityManager;

    HelperExtension helperExtension = new HelperExtension();
    List<Predicate> predicateList;
    String message = "";

    @Override
    public List<User> get(Long id, String username, String phoneNo, Boolean isFlag,DatabaseHelper databaseHelper) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        predicateList = new ArrayList<>();
        if(id>0){
            predicateList.add(criteriaBuilder.equal(root.get("id"),id));
        }
        if (!helperExtension.isNullOrEmpty(username)){
            predicateList.add(criteriaBuilder.equal(root.get("username"),username));
        }
        if (!helperExtension.isNullOrEmpty(phoneNo)){
            predicateList.add(criteriaBuilder.equal(root.get("phoneNo"),phoneNo));
        }
        predicateList.add(criteriaBuilder.equal(root.get("isFlag"),isFlag));
        if (!helperExtension.isNullOrEmpty(databaseHelper)) {
            // Search Starts
            if (!helperExtension.isNullOrEmpty(databaseHelper.getSearch())) {
                predicateList.add(criteriaBuilder.like(root.get(""), databaseHelper.getSearch() + "%"));
            }
            // Sorting Starts
            if (databaseHelper.getSortOrder().equalsIgnoreCase(EnumsExtension.eOrderBy.enAsc.getKey())) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(databaseHelper.getSortBy())));
            } else if (databaseHelper.getSortOrder().equalsIgnoreCase(EnumsExtension.eOrderBy.enDesc.getKey())) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(databaseHelper.getSortBy())));
            }
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            // Pagination Starts
            if (databaseHelper.getCurrentPage() != 0 && databaseHelper.getItemPerPage() != 0) {
                final TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
                typedQuery.setFirstResult((databaseHelper.getCurrentPage() - 1) * databaseHelper.getItemPerPage());
                typedQuery.setMaxResults(databaseHelper.getItemPerPage());
                return typedQuery.getResultList();
            }
        } else {
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public String exist(User entity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        // check if Id exist!
        if (entity.getId() > 0) {
            predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.notEqual(root.get("id"), entity.getId()));
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            if (entityManager.createQuery(criteriaQuery).getResultList().size() > 0) {
                return ConstantExtension.ACCOUNT_ID_ALREADY_EXIST;
            }
        }
        // check if username exist!
        if(!helperExtension.isNullOrEmpty(entity.getUsername())){
            predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(root.get("username"), entity.getUsername()));
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            if (entityManager.createQuery(criteriaQuery).getResultList().size() > 0) {
                return ConstantExtension.USER_NAME_ALREADY_EXIST;
            }
        }
        // checking if email id exist!
        if (!helperExtension.isNullOrEmpty(entity.getEmailId())) {
            predicateList = new ArrayList<>();
            predicateList.add(criteriaBuilder.equal(root.get("emailId"), entity.getEmailId()));
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
            if (entityManager.createQuery(criteriaQuery).getResultList().size() > 0) {
                return ConstantExtension.EMAIL_ID_ALREADY_EXIST;
            }
        }
        // checking is phone no exist
//        if (!helperExtension.isNullOrEmpty(entity.getPhoneNo())) {
//            predicateList = new ArrayList<>();
//            predicateList.add(criteriaBuilder.equal(root.get("mobile"), entity.getPhoneNo()));
////            predicateList.add(criteriaBuilder.equal(root.get("isFlag"), 1));
//            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
//            if (entityManager.createQuery(criteriaQuery).getResultList().size() > 0) {
//                return ConstantExtension.PHONE_NO_ALREADY_EXIST;
//            }
//        }
            return message;
    }
}
