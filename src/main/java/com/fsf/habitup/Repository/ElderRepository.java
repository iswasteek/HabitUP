package com.fsf.habitup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Adult;
import com.fsf.habitup.entity.Elder;

@Repository
public interface ElderRepository extends JpaRepository<Elder, Long> {

	@Query(value = "INSERT INTO elder VALUES (:name,:email, :password, :dob, :accountStatus, :subscriptionType, :profilephto)", nativeQuery = true)
    public Adult addElder(String name, String email, String password, String dob, String accountStatus,String subscriptionType, String profilephoto);
	
	@Query(value = "SELECT * FROM child WHERE elderId = :elder_Id", nativeQuery = true)
    public List<Elder> getElder(Long elderid);
	
	@Query(value = "UPDATE user SET name=:name,email= :email, password = :password, dob= :dob, profilephoto= :profilephoto WHERE elderId = :elder_Id", nativeQuery = true)
    public String updateElder(String name, String email, String password, String dob, String profilephoto);
}
