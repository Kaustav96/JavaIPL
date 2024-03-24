package com.kaustav.ipl.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Points")
@ToString
public class PointsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "teamName")
    private String teamName;

    @Column(name = "matches",columnDefinition = "int default '0'")
    private int matches;
    @Column(name = "won",columnDefinition = "int default '0'")
    private int won;
    @Column(name = "lost",columnDefinition = "int default '0'")
    private int lost;
    @Column(name = "tied",columnDefinition = "int default '0'")
    private int tied;
    @Column(name = "noResult",columnDefinition = "int default '0'")
    private int nr;
    @Column(name = "points",columnDefinition = "int default '0'")
    private int pts;
    @Column(name = "netRunRate",columnDefinition = "DOUBLE default 0.0")
    private Double nrr;

}
