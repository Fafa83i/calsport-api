package com.fcalvo.calsport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcalvo.calsport.model.Token;
import com.fcalvo.calsport.model.User;
import com.fcalvo.calsport.repository.TokenRepository;

import lombok.Data;

@Data
@Service
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    public Optional<Token> getToken(final Long id){
        return this.tokenRepository.findById(id);
    }

    public List<Token> getTokens(User user){
        return this.tokenRepository.findByUserId(user.getId());
    }

    public Token save(Token token){
        Token savedToken = this.tokenRepository.save(token);
        return savedToken;
    }

    public Token getTokenByValue(String value){
        return this.tokenRepository.findByValue(value);
    }

    public Boolean isValidToken(String value){
        Token token = this.tokenRepository.findByValue(value);
        if(token != null){
            return true;
        }
        return false;
    }
    
}
