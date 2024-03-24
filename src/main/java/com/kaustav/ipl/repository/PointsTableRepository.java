package com.kaustav.ipl.repository;

import com.kaustav.ipl.model.PointsTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsTableRepository extends JpaRepository<PointsTable,Long> {
    PointsTable findByTeamName(String teamName);
}
