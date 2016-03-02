package com.softserve.hotels.dao;

import com.softserve.hotels.model.Configs;

public interface ConfigsDao extends AbstractDao<Configs> {
    
    Configs getConfigsByFeature(String name);

}
