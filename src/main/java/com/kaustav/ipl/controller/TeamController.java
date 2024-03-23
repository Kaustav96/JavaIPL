package com.kaustav.ipl.controller;

import com.kaustav.ipl.exception.TeamNotFoundException;
import com.kaustav.ipl.model.Team;
import com.kaustav.ipl.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable long id){
        Team team = teamRepository.findById(id).orElseThrow(
                ()-> new TeamNotFoundException("Team doesnt exist for id - "+id)
        );
        return ResponseEntity.ok(team);
    }
    @PostMapping
    public Team createTeam(@RequestBody Team team){
        return teamRepository.save(team);
    }
}
