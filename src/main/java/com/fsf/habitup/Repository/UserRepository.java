package com.fsf.habitup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.Enums.UserType;
import com.fsf.habitup.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    public void deleteByEmail(String email);

    List<User> findByUserType(UserType userType);

    boolean existsByPhoneNo(Long phoneNo);

}
