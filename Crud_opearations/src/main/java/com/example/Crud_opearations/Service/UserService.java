package com.example.Crud_opearations.Service;

import com.example.Crud_opearations.Entity.UserEntity;
import com.example.Crud_opearations.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public boolean authenticate(String email, String password) {


        UserEntity user = userRepository.findByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email); // Assumes this method is implemented in UserRepository
    }
}
