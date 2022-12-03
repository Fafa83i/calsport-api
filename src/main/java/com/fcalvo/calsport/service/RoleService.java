package com.fcalvo.calsport.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fcalvo.calsport.model.Role;
import com.fcalvo.calsport.repository.RoleRepository;

import lombok.Data;

@Data
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getRoleById(final Long id){
        return this.roleRepository.findById(id);
    }

    public Role getRole(String name){
        return this.roleRepository.findByName(name);
    }

    public Role save(Role role){
        Role savedRole = this.roleRepository.save(role);
        return savedRole;
    }
    
}
