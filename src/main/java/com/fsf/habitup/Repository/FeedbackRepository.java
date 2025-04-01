package com.fsf.habitup.Repository;

import com.fsf.habitup.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    @Query("SELECT f FROM Feedback f WHERE f.program.program_id = :programId")
    List<Feedback> findByProgram_ProgramId(@Param("programId") Long programId);


    List<Feedback> findByUser_UserIdOrderByFeedbackDateDesc(Long userId);

    @Query("SELECT AVG(f.ratings) FROM Feedback f WHERE f.program.program_id = ?1")
    Double calculateAverageRatingByProgramId(Long programId);

    List<Feedback> findByFeedbackDateAfter(Date cutoffDate);

}
