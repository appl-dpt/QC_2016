package com.softserve.hotels.dao;

import org.springframework.stereotype.Repository;

import com.softserve.hotels.model.Role;

@Repository("roleDao")
public class RoleDaoImpl extends AbstractDaoImpl<Role> implements RoleDao {

}
