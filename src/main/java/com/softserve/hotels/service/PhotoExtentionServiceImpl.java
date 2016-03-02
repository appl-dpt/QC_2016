package com.softserve.hotels.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.model.PhotoExtention;

@Service("photoExtentionService")
@Transactional
public class PhotoExtentionServiceImpl extends AbstractServiceImpl<PhotoExtention> implements PhotoExtentionService {

    @Override
    public void create(PhotoExtention entity) {
        entity.setExtention(entity.getExtention().toLowerCase());
        super.create(entity);
    }

}
