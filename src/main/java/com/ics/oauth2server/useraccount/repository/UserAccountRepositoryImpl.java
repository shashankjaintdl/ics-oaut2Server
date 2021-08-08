package com.ics.oauth2server.useraccount.repository;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.BaseRepositoryImpl;
import com.ics.oauth2server.helper.DatabaseHelper;
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
public class UserAccountRepositoryImpl extends BaseRepositoryImpl<UserAccount> implements UserAccountRepository {
    @PersistenceContext
    private EntityManager entityManager;
    HelperExtension helperExtension = new HelperExtension();
    List<Predicate> predicateList;
    String message = "";

    @Override
    public List<UserAccount> get(Long userAccountId, String username, String emailId, Boolean isFlag, DatabaseHelper databaseHelper) {
        predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserAccount> criteriaQuery = criteriaBuilder.createQuery(UserAccount.class);
        Root<UserAccount> root = criteriaQuery.from(UserAccount.class);
        if(userAccountId>0){
            predicateList.add(criteriaBuilder.equal(root.get("id"), userAccountId));
        }
        if(!helperExtension.isNullOrEmpty(username)){
            predicateList.add(criteriaBuilder.equal(root.get("username"), username));
        }
        if (!helperExtension.isNullOrEmpty(emailId)){
            predicateList.add(criteriaBuilder.equal(root.get("email"), emailId));
        }
        if(!helperExtension.isNullOrEmpty(isFlag)){
            predicateList.add(criteriaBuilder.equal(root.get("isFlag"), isFlag));
        }
        if(!helperExtension.isNullOrEmpty(databaseHelper)){

        }
        else {
            criteriaQuery.where(criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()])));
        }
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
