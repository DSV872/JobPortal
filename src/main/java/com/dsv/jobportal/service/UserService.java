package com.dsv.jobportal.service;

import com.dsv.jobportal.model.*;
import com.dsv.jobportal.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User addUser(User regUser) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        regUser.setPassword(encoder.encode(regUser.getPassword()));
        String roleName = regUser.getRoles().getName();
        Role role = roleRepository.findByName(roleName);
        if(role == null){
            throw new RuntimeException("Role not found");
        }
        regUser.setRoles(role);
        return userRepository.save(regUser);
    }

}

