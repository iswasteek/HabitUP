package com.fsf.habitup.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Adult;

@Repository
public interface AdultRepository extends JpaRepository<Adult, Long> {

	@Query(value = "INSERT INTO adult VALUES (:name,:email, :password, :dob, :accountStatus, :subscriptionType, :profilephto)", nativeQuery = true)
    public Adult addAdult(String name, String email, String password, String dob, String accountStatus,
            String subscriptionType, String profilephoto);

	@Query(value = "SELECT * FROM adult WHERE adultId = :adult_Id", nativeQuery = true)
    public List<Adult> getAdult(Long adultid);

	@Query(value = "UPDATE user SET name=:name,email= :email, password = :password, dob= :dob, profilephoto= :profilephoto WHERE adultId = :adult_Id", nativeQuery = true)
    public String updateAdult(String name, String email, String password, String dob, String profilephoto);

}
