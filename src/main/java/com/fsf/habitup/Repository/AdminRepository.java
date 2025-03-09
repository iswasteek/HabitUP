package com.fsf.habitup.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
