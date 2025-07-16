package com.fsf.habitup.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsf.habitup.Enums.DocumentStatus;
import com.fsf.habitup.entity.Documents;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {
    Optional<Documents> findByUser_UserId(Long userId);
    List<Documents> findByStatus(DocumentStatus status);
}
