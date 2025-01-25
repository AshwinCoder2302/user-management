package com.user.management.service.impl;

import com.user.management.constant.Constant;
import com.user.management.dto.UserRequestDTO;
import com.user.management.dto.UserResponseDTO;
import com.user.management.entity.User;
import com.user.management.repository.UserRepository;
import com.user.management.service.UserService;
import com.user.management.util.CommonUtils;
import com.user.management.util.GenericDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GenericDao genericDao;

    private final CommonUtils commonUtils;

    private final PasswordEncoder encoder;


    @Override
    public UserResponseDTO getUserProfile() {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            BeanUtils.copyProperties(user, userResponseDTO);
        }
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getUsers(Integer pageNo, Integer pageSize, String sortBy, String sortMethod) {
        List<User> savedUser = userRepository.findAll();
        return commonUtils.mapEntityListToDTOList(savedUser, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO getUser(UUID id) {
        User user = genericDao.findById(id, userRepository, Constant.USER);
        return commonUtils.mapEntityToDTO(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, UUID id) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        User user = genericDao.findById(id, userRepository, Constant.USER);
        BeanUtils.copyProperties(userRequestDTO, user);
        User updatedUser = userRepository.save(user);
        BeanUtils.copyProperties(updatedUser, userResponseDTO);
        return userResponseDTO;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        User user = new User();
        BeanUtils.copyProperties(userRequestDTO, user);
        user.setPassword(encoder.encode("12591981"));
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, userResponseDTO);
        return userResponseDTO;
    }

    @Override
    public String deleteUser(UUID id) {
        User user = genericDao.findById(id, userRepository, Constant.USER);
        userRepository.delete(user);
        return Constant.DELETED_SUCCESSFULLY;
    }

}
