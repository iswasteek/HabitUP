package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Elder;

@Repository
public interface ElderRepository extends JpaRepository<Elder, Long> {

    // public Adult addElder(String name, String email, String password, String dob,
    // String accountStatus,String subscriptionType, String profilePhoto);
    //
    //
    // public List<Elder> getElder(Long elderId);
    //
    //
    // public String updateElder(String name, String email, String password, String
    // dob, String profilePhoto);
    //
}
