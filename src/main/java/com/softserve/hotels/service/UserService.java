package com.softserve.hotels.service;

import java.util.List;

import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;

public interface UserService extends AbstractService<User> {

    void create(User entity);
    
    User update(User entity);

    User findUserByNick(String nickname);

    User findUserByEmail(String email);

    List<User> findUserByRole(Role role);

    List<User> findUserLikeEmailAndByRolePageable(String email, Role role, PaginationInfoDto userListPageInfo);
    
    List<User> findAllUsersPageable(PaginationInfoDto userListPageInfo);
    
    List<User> findUserRenterUnapproved(Role role);
}
