package com.fcalvo.calsport.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fcalvo.calsport.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findById(Long id);

    Role findByName(String name);
    
}
