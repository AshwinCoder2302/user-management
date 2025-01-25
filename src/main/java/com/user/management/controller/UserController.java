package com.user.management.controller;

import com.user.management.constant.Constant;
import com.user.management.dto.ResponseDTO;
import com.user.management.dto.UserRequestDTO;
import com.user.management.dto.UserResponseDTO;
import com.user.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseDTO<UserResponseDTO> getUserDetails() {
        return new ResponseDTO<>(HttpStatus.OK.value(), Constant.SUCCESS, userService.getUserProfile());
    }

    @GetMapping
    public ResponseDTO<List<UserResponseDTO>> getUsers(@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortMethod) {
        return new ResponseDTO<>(HttpStatus.OK.value(), Constant.SUCCESS, userService.getUsers(pageNo, pageSize, sortBy, sortMethod));
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserResponseDTO> getUserById(@PathVariable UUID id){
        return new ResponseDTO<>(HttpStatus.OK.value(), Constant.SUCCESS, userService.getUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseDTO<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequest, @PathVariable UUID id){
        return new ResponseDTO<>(HttpStatus.OK.value(), Constant.SUCCESS, userService.updateUser(userRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<String> deleteUser(@PathVariable UUID id){
        return new ResponseDTO<>(HttpStatus.OK.value(), Constant.SUCCESS, userService.deleteUser(id));
    }

    @PostMapping
    public ResponseDTO<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseDTO<>(HttpStatus.OK.value(), Constant.SUCCESS, userService.createUser(userRequestDTO));
    }
}
