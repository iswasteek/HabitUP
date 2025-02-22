package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Adult;

@Repository
public interface AdultRepository extends JpaRepository<Adult, Long> {

    // public Adult addAdult(String name, String email, String password, String dob,
    // String accountStatus,
    // String subscriptionType, String profilePhoto);
    //
    //
    // public List<Adult> getAdult(Long adultId);
    //
    //
    // public String updateAdult(String name, String email, String password, String
    // dob, String profilePhoto);

}
