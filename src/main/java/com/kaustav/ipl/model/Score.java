package com.kaustav.ipl.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "runs",columnDefinition = "int default '0'")
    private int runs;
    @Column(name = "wickets",columnDefinition = "int default '0'")
    private int wickets;
    @Column(name = "oversPlayed",columnDefinition = "DOUBLE default 0.0")
    private Double oversPlayed = 0.0;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
