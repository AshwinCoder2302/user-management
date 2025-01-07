package com.user.management.service;

import com.user.management.dto.UserRequestDTO;
import com.user.management.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO getUserProfile();

    List<UserResponseDTO> getUsers(Integer pageNo, Integer pageSize, String sortBy, String sortMethod);

    UserResponseDTO getUser(UUID id);

    UserResponseDTO updateUser(UserRequestDTO userRequest, UUID id);

    String deleteUser(UUID id);

}
