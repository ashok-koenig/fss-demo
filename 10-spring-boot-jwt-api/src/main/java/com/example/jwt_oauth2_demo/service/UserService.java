package com.example.jwt_oauth2_demo.service;

import com.example.jwt_oauth2_demo.repository.UserRepository;
import com.example.jwt_oauth2_demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found!"));
    }
    @Transactional
    public User register(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
