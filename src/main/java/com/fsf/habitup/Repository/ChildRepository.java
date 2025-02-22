package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    // public Child addChild(String name, String email, String password, String dob,
    // String accountStatus,
    // String subscriptionType, String profilePhoto);
    //
    //
    // public List<Child> getChild(Long childId);
    //
    //
    // public String updateChild(String name, String email, String password, String
    // dob, String profilePhoto);
}
