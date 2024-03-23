package com.kaustav.ipl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Points")
public class PointsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "teamName")
    private String teamName;

    @Column(name = "matches")
    private int matches;
    @Column(name = "won")
    private int won;
    @Column(name = "lost")
    private int lost;
    @Column(name = "tied")
    private int tied;
    @Column(name = "noResult")
    private int nr;
    @Column(name = "points")
    private int pts;
    @Column(name = "netRunRate")
    private int nrr;
}
