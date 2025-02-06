package com.fsf.habitup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Child;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

	@Query(value = "INSERT INTO child VALUES (:name,:email, :password, :dob, :accountStatus, :subscriptionType, :profilephto)", nativeQuery = true)
    public Child addChild(String name, String email, String password, String dob, String accountStatus,
            String subscriptionType, String profilephoto);

	@Query(value = "SELECT * FROM child WHERE childId = :childId", nativeQuery = true)
    public List<Child> getChild(Long childId);

	@Query(value = "UPDATE user SET name=:name,email= :email, password = :password, dob= :dob, profilephoto= :profilephoto WHERE childId = :childId", nativeQuery = true)
    public String updateChild(String name, String email, String password, String dob, String profilephoto);
}
