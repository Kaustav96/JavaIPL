package com.kaustav.ipl.controller;

import com.kaustav.ipl.exception.PlayerNotFoundException;
import com.kaustav.ipl.exception.TeamNotFoundException;
import com.kaustav.ipl.model.PlayerData;
import com.kaustav.ipl.model.Team;
import com.kaustav.ipl.repository.PlayerRepository;
import com.kaustav.ipl.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/players")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerData> getPlayerById(@PathVariable long id){
        PlayerData playerData = playerRepository.findById(id).orElseThrow(
                () -> new PlayerNotFoundException("Player with id "+ id +" does not exist.")
        );

        return ResponseEntity.ok(playerData);
    }

    @PostMapping
    public PlayerData createPlayer(@RequestBody PlayerData playerData){
        Team teamData = teamRepository.findById(playerData.getTeam().getId()).orElseThrow(
                () -> new TeamNotFoundException("Team with id -"+ playerData.getTeam().getId()+" does not exist.")
        );
        playerData.setTeam(teamData);
        return playerRepository.save(playerData);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlayerData> pathPLayerById(@PathVariable long id,
                                                     @RequestBody PlayerData playerData){
        PlayerData data = playerRepository.findById(id).orElseThrow(
                ()-> new PlayerNotFoundException("Player with id "+id+" doesnt exist")
        );
        data.setName(playerData.getName()==null?data.getName(): playerData.getName());
        data.setRole(playerData.getRole()==null?data.getRole(): playerData.getRole());
        playerRepository.save(data);
        return ResponseEntity.ok(data);
    }

}
