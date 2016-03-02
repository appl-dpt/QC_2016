package com.softserve.hotels.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.User;
import com.softserve.hotels.model.VerificationToken;

@Repository
public class VerificationTokenDaoImpl extends AbstractDaoImpl<VerificationToken> implements VerificationTokenDao {

    @Override
    public void delete(VerificationToken entity) {
        super.delete(getEntityManager().merge(entity));
    }

    @Override
    public VerificationToken findByUser(User user) {
        Query query = getEntityManager().createQuery("from VerificationToken where user.id = :user");
        query.setParameter("user", user.getId());
        return (VerificationToken) query.getSingleResult();
    }

    @Override
    public VerificationToken findByToken(String token) {
        Query query = getEntityManager().createQuery("from VerificationToken where token = :token");
        query.setParameter("token", token);
        return (VerificationToken) query.getSingleResult();
    }

}
