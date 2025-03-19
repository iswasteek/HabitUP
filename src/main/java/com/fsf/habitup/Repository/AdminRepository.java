package com.fsf.habitup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @EntityGraph(attributePaths = {"permissions"})
    Admin findByEmail(String email);

    List<Admin> findAllByUserType(UserType admin);
}
