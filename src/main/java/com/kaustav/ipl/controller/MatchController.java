package com.kaustav.ipl.controller;

import com.kaustav.ipl.exception.MatchDoesNotExistException;
import com.kaustav.ipl.exception.TeamNotFoundException;
import com.kaustav.ipl.model.Match;
import com.kaustav.ipl.model.Team;
import com.kaustav.ipl.repository.MatchRepository;
import com.kaustav.ipl.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/matches")
public class MatchController {

    // add match details
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @PostMapping
    public Match addMatch(@RequestBody Match match){
        Team team1 = teamRepository.findById(match.getTeam1().getId()).orElseThrow(
                () -> new TeamNotFoundException("Team with id -"+ match.getTeam1().getId()+" does not exist.")
        );
        Team team2 = teamRepository.findById(match.getTeam1().getId()).orElseThrow(
                () -> new TeamNotFoundException("Team with id -"+ match.getTeam2().getId()+" does not exist.")
        );

        return matchRepository.save(match);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id){
        Match match = matchRepository.findById(id).orElseThrow(
                () -> new MatchDoesNotExistException("Match doesn't exist")
        );
        return ResponseEntity.ok(match);
    }
}
