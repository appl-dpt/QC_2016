package com.softserve.hotels.service;

import com.softserve.hotels.model.Configs;

public interface ConfigsService extends AbstractService<Configs> {

    Configs getConfigsByFeature(String name);

}
