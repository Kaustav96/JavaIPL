package com.kaustav.ipl.repository;

import com.kaustav.ipl.model.PlayerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerData,Long> {
}
