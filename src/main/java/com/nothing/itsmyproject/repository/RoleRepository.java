package com.nothing.itsmyproject.repository;

import com.nothing.itsmyproject.entity.IRole;
import com.nothing.itsmyproject.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(IRole name);

}
