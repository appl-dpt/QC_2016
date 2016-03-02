package com.softserve.hotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.ConfigsDao;
import com.softserve.hotels.model.Configs;

@Service
@Transactional
public class ConfigsServiceImpl extends AbstractServiceImpl<Configs> implements ConfigsService {

    @Autowired
    private ConfigsDao configsDao;

    @Override
    public Configs getConfigsByFeature(String features) {
        return configsDao.getConfigsByFeature(features);
    }

}
