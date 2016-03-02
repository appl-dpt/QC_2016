package com.softserve.hotels.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.hotels.dao.ActionApartmentDao;
import com.softserve.hotels.dao.ApartmentDAO;
import com.softserve.hotels.dao.UserDao;
import com.softserve.hotels.dto.PaginationInfoDto;
import com.softserve.hotels.model.ActionApartment;
import com.softserve.hotels.model.Apartment;
import com.softserve.hotels.model.ApartmentStatus;
import com.softserve.hotels.model.Role;
import com.softserve.hotels.model.User;
import com.softserve.hotels.utils.FileUtils;

@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

    private static final String DEFAULT_AVATAR = "img/defaultAvatar.jpg";

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired 
    private ApartmentDAO apartmentDao;
    
    @Autowired
    private ActionApartmentDao actionApartmentDao;

    @Override
    public void create(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setDateRegistration(new LocalDate());
        entity.setImageLink(DEFAULT_AVATAR);
        super.create(entity);
        if (entity.getRole() == Role.RENTER)
            FileUtils.makeDirRenter(entity.getId());
    }
    
    @Override
    public User update(User user) {
        User oldUser = dao.findById(user.getId());
        if (user.getRole() == Role.RENTER && oldUser.isEnabled() == true && user.isEnabled() == false) {
            for (Apartment apartment : user.getRentAppartments()) {
                apartment.setStatus(ApartmentStatus.DISABLED_BY_ADMIN);
                apartmentDao.update(apartment);
                ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.DISABLED_BY_ADMIN);
                actionApartmentDao.create(actionApartment);
            }
        }
        if (user.getRole() == Role.RENTER && oldUser.isEnabled() == false && user.isEnabled() == true) {
            for (Apartment apartment : user.getRentAppartments()) {
                apartment.setStatus(ApartmentStatus.ENABLED);
                apartmentDao.update(apartment);
                ActionApartment actionApartment = new ActionApartment(apartment, ApartmentStatus.ENABLED);
                actionApartmentDao.create(actionApartment);
            }
        }
        return dao.update(user);
    }

    @Override
    public User findUserByNick(String nick) {

        return dao.findUserByNickname(nick);
    }

    @Override
    public List<User> findUserByRole(Role role) {
        return dao.findUserByRole(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return dao.findUserByEmail(email);
    }

    @Override
    public List<User> findUserLikeEmailAndByRolePageable(String email, Role role, PaginationInfoDto userListPageInfo) {
        return dao.findUserLikeEmailAndByRolePageable(email, role, userListPageInfo);
    }

    @Override
    public List<User> findAllUsersPageable(PaginationInfoDto userListPageInfo) {
        
        return dao.findAllUsersPageable(userListPageInfo);
    }

    @Override
    public List<User> findUserRenterUnapproved(Role role) {
        return dao.findUserRenterUnapproved(role);
    }

}
