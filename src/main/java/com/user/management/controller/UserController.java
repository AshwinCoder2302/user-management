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

    @GetMapping
    public ResponseDTO<List<UserResponseDTO>> getUsers(@RequestParam int pageNo, @RequestParam int pageSize,
                                                       @RequestParam String sortBy, @RequestParam String sortMethod) {
        return new ResponseDTO<>(HttpStatus.OK, Constant.SUCCESS, userService.getUsers(pageNo, pageSize, sortBy, sortMethod));
    }

    @GetMapping("/{id}")
    public ResponseDTO<UserResponseDTO> getUserById(@PathVariable UUID id){
        return new ResponseDTO<>(HttpStatus.OK, Constant.SUCCESS, userService.getUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseDTO<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequest, @PathVariable UUID id){
        return new ResponseDTO<>(HttpStatus.OK, Constant.SUCCESS, userService.updateUser(userRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<String> deleteUser(@PathVariable UUID id){
        return new ResponseDTO<>(HttpStatus.OK, Constant.SUCCESS, userService.deleteUser(id));
    }
}
