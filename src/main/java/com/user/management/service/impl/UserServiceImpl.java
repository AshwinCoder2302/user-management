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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final GenericDao genericDao;

    private final CommonUtils commonUtils;

    @Override
    public List<UserResponseDTO> getUsers(int pageNo, int pageSize, String sortBy, String sortMethod) {
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
    public String deleteUser(UUID id) {
        User user = genericDao.findById(id, userRepository, Constant.USER);
        userRepository.delete(user);
        return Constant.DELETED_SUCCESSFULLY;
    }

}
