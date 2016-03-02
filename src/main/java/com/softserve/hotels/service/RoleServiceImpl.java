package com.softserve.hotels.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.model.Role;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends AbstractServiceImpl<Role> implements RoleService {

}
