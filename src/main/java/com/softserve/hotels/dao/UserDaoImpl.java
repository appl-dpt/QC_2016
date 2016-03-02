package com.softserve.hotels.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;

/**
 * @author Rogulya Volodymyr
 */
@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    public static final Logger LOG = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User findUserByNickname(String nickname) {
        LOG.debug("Start");
        try {
            Query query = getEntityManager().createQuery("from User WHERE nickname = :nick");
            query.setParameter("nick", nickname);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("Such user is not found");
            LOG.debug(e);
            return null;
        }
    }

    @Override
    public List<User> findUserByRole(Role role) {
        TypedQuery<User> query = getEntityManager().createNamedQuery(User.NQ_FIND_ALL_BY_ROLE, User.class);
        return query.setParameter("role", role).getResultList();
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            Query query = getEntityManager().createQuery("from User WHERE email = :email");
            query.setParameter("email", email);

            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("Such user is not found");
            LOG.debug(e);
            return null;
        }
    }

    @Override
    public List<User> findUserLikeEmailAndByRolePageable(String email, Role role, PaginationInfoDto userListPageInfo) {
        int currentPage = userListPageInfo.getCurrentPage();
        int pageSize = userListPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        if (!"".equals(email)) {
            String emailPattern = "%" + email + "%";
            Predicate emailPredicate = criteriaBuilder.like(root.<String> get("email"), emailPattern);
            predicates.add(emailPredicate);
        }
        if (role != null) {
            Predicate rolePredicate = criteriaBuilder.equal(root.<Role>get("role"), role);
            predicates.add(rolePredicate);
        }
        if (predicates.size() > 0) {
            query.where(predicates.toArray(new Predicate[predicates.size()]));
        }
        List<User> result = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        userListPageInfo.setPageCount(pageCount);
        return result;
    }

    @Override
    public List<User> findAllUsersPageable(PaginationInfoDto userListPageInfo) {
        int currentPage = userListPageInfo.getCurrentPage();
        int pageSize = userListPageInfo.getPageSize();
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        List<User> resultList = findByRange(query, (currentPage - 1) * pageSize, pageSize);
        Long pageCount = getEntityPagesCount(getEntityCount(criteriaBuilder, query, root), pageSize);
        userListPageInfo.setPageCount(pageCount);
        return resultList;
    }
    @Override
    public List<User> findUserRenterUnapproved(Role role) {
        try {
            Query query = getEntityManager().createQuery("FROM User WHERE role = :role AND enabled = false");
            query.setParameter("role", role);

            return query.getResultList();
        } catch (NoResultException e) {
            LOG.info("Such users is not found");
            LOG.debug(e);
            return null;
        }
    }

}
