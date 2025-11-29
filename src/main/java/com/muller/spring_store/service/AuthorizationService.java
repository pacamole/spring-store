package com.muller.spring_store.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.muller.spring_store.dto.RegisterDTO;
import com.muller.spring_store.mapper.UserMapper;
import com.muller.spring_store.model.User;
import com.muller.spring_store.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }

    public void register(RegisterDTO data) {
        if (this.repository.findByEmail(data.getEmail()) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User newUser = mapper.toEntity(data);

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        newUser.setPassword(encryptedPassword);

        this.repository.save(newUser);
    }

}
