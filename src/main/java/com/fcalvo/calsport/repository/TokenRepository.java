package com.fcalvo.calsport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fcalvo.calsport.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findById(Long id);

    Token findByValue(String value);

    List<Token> findByUserId(Long userId);
    
}
