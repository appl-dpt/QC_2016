package com.softserve.hotels.dao;

import java.util.List;

import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;

public interface UserDao extends AbstractDao<User> {

    User findUserByNickname(String nickname);

    User findUserByEmail(String email);

    List<User> findUserByRole(Role role);

    List<User> findUserLikeEmailAndByRolePageable(String email, Role role, PaginationInfoDto userPageInfo);
    
    List<User> findAllUsersPageable(PaginationInfoDto userListPageInfo);
    
    List<User> findUserRenterUnapproved(Role role);
}