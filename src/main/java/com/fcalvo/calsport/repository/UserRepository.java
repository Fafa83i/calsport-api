package com.fcalvo.calsport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fcalvo.calsport.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


    User findByEmail(String email); 

    User findByPseudo(String pseudo);
    
}
