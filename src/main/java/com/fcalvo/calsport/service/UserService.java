package com.fcalvo.calsport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcalvo.calsport.model.User;
import com.fcalvo.calsport.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional<User> getUser(final long id){
        return userRepository.findById(id);
    }
    public User getUserByMail(final String mail){
        return userRepository.findByEmail(mail);
    }
    public User getUserByUsername(final String username){
        return userRepository.findByPseudo(username);
    }

    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }

    public void deleteUserById(final Long id){
        this.userRepository.deleteById(id);
    }

    public User saveUser(User user){
        User savedUser = this.userRepository.save(user);
        return savedUser;
    }



    
}
