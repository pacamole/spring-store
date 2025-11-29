package com.muller.spring_store.mapper;

import org.springframework.stereotype.Component;

import com.muller.spring_store.dto.RegisterDTO;
import com.muller.spring_store.model.User;

@Component
public class UserMapper {

    public User toEntity(RegisterDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }
}
