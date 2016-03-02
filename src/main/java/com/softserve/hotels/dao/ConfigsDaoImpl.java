package com.softserve.hotels.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Configs;

@Repository("configsDao")
public class ConfigsDaoImpl extends AbstractDaoImpl<Configs> implements ConfigsDao {
    
    public static final Logger LOG = LogManager.getLogger(ConfigsDaoImpl.class);

    @Override
    public Configs getConfigsByFeature(String features) {
        
        try {
            Query query = getEntityManager().createQuery("from Configs WHERE features = :features");
            query.setParameter("features", features);
            return (Configs) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.info("There is no such feature" + e);
        return null;
    }
}
    
}

