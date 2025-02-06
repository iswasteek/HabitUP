package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "INSERT INTO user VALUES (:user_id, :name,:email, :password, :dob, :joinDate)", nativeQuery = true)
    public User addUser(Long user_id, String email, String name, String password, String dob, String joinDate);

	@Query(value = "INSERT INTO user VALUES (:name,:email, :password, :dob, :accountStatus, :subscriptionType, :profilephto)", nativeQuery = true)
    public User findByUserId(Long user_id);

	@Query(value = "SELECT * FROM user WHERE Email = :email", nativeQuery = true)
    User findByEmail(String email);

	@Query(value = "INSERT INTO elder VALUES (:name,:email, :password, :dob, :accountStatus, :subscriptionType, :profilephto)", nativeQuery = true)
    public void updateByUserId(String name, String email, String password, String dob);

	@Query(value = "SELECT password FROM user WHERE Email = :email", nativeQuery = true)
    public String getPasswordByEmail(String email);
}
