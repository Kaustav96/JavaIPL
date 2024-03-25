package com.kaustav.ipl.repository;

import com.kaustav.ipl.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score,Long> {
    @Query("SELECT s FROM Score s WHERE s.match.id = :matchId AND s.team.id = :teamId")
    Score findByMatchIdAndTeamId(@Param("matchId") Long matchId, @Param("teamId") Long teamId);
    @Query("SELECT s FROM Score s WHERE s.match.id = :matchId")
    List<Score> findByMatchId(@Param("matchId") Long matchId);
}
