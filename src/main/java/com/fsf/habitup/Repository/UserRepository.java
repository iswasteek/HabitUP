package com.fsf.habitup.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    User findByEmail(String email);

}
