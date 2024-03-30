package com.kaustav.ipl.controller;

import com.kaustav.ipl.comparators.PointsTableComparator;
import com.kaustav.ipl.exception.MatchDoesNotExistException;
import com.kaustav.ipl.exception.TeamNotFoundException;
import com.kaustav.ipl.model.Match;
import com.kaustav.ipl.model.PointsTable;
import com.kaustav.ipl.model.Score;
import com.kaustav.ipl.model.Team;
import com.kaustav.ipl.repository.MatchRepository;
import com.kaustav.ipl.repository.PointsTableRepository;
import com.kaustav.ipl.repository.ScoreRepository;
import com.kaustav.ipl.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/scores")
public class ScoreController {

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private PointsTableRepository tableRepository;

    private HashMap<Long,Boolean> matchPointsCalculated = new HashMap<>();
    @GetMapping("/{matchId}")
    public ResponseEntity<List<Score>> getScoreByMatchId(@PathVariable long matchId){
        List<Score> score = scoreRepository.findByMatchId(matchId);
        if(score!=null){
            return new ResponseEntity<>(score,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public Score addScore(@RequestBody Score score){
        matchRepository.findById(score.getMatch().getId()).orElseThrow(
                ()-> new MatchDoesNotExistException("Match doesn't exist")
        );
        teamRepository.findById(score.getTeam_id()).orElseThrow(
                ()-> new TeamNotFoundException("Team doesn't exist")
        );

        return scoreRepository.save(score);
    }

    @GetMapping("/calculatePoints/{matchId}")
    public ResponseEntity calculatePoints(@PathVariable long matchId){
        Match match = matchRepository.findById(matchId).orElseThrow(
                ()->new MatchDoesNotExistException("Match not found")
        );
        if(!matchPointsCalculated.containsKey(matchId)) {

            // get team details
            Team team1 = match.getTeam1();
            Team team2 = match.getTeam2();

            // get score data for both teams
            Score scoreT1 = scoreRepository.findByMatchIdAndTeamId(matchId, team1.getId());
            Score scoreT2 = scoreRepository.findByMatchIdAndTeamId(matchId, team2.getId());

            // get current status of points table for both teams
            PointsTable team1Points = tableRepository.findByTeamName(team1.getTeamName());
            PointsTable team2Points = tableRepository.findByTeamName(team2.getTeamName());

            // no result check due to rain
            if (scoreT1.getRuns() == 0 || scoreT2.getRuns() == 0) {
                team1Points.setNr(team1Points.getNr() + 1);
                team2Points.setNr(team2Points.getNr() + 1);
            }

            // compare runs scored to check who is the winner and update the points
            if (scoreT1.getRuns() == scoreT2.getRuns()) {
                team1Points.setTied(team1Points.getTied() + 1);
                team2Points.setTied(team2Points.getTied() + 1);
                team1Points.setPts(team1Points.getPts() + 1);
                team2Points.setPts(team2Points.getPts() + 1);

            } else if (scoreT1.getRuns() > scoreT2.getRuns()) {
                team1Points.setWon(team1Points.getWon() + 1);
                team2Points.setLost(team2Points.getLost() + 1);
                team1Points.setPts(team1Points.getPts() + 2);
            } else {
                team2Points.setWon(team2Points.getWon() + 1);
                team1Points.setLost(team1Points.getLost() + 1);
                team2Points.setPts(team2Points.getPts() + 2);
            }


            // update the total number of matches being played.
            int team1TotalMatchesPlayed = team1Points.getMatches();
            int team2TotalMatchesPlayed = team2Points.getMatches();
            team1TotalMatchesPlayed = team1Points.getNr() + team1Points.getWon() + team1Points.getLost() + team1Points.getTied();
            team2TotalMatchesPlayed = team2Points.getNr() + team2Points.getWon() + team2Points.getLost() + team2Points.getTied();

            team1Points.setMatches(team1TotalMatchesPlayed);
            team2Points.setMatches(team2TotalMatchesPlayed);


            // calculate net run rate;

            double netRunRateT1 = calculateNetRunRate(scoreT1, scoreT2);
            double netRunRateT2 = calculateNetRunRate(scoreT2, scoreT1);

            team1Points.setNrr((team1Points.getNrr() == null ? 0.0 : team1Points.getNrr()) + netRunRateT1);
            team2Points.setNrr((team2Points.getNrr() == null ? 0.0 : team2Points.getNrr()) + netRunRateT2);

            List<PointsTable> pointsTables = Arrays.asList(team1Points, team2Points);

            savePointsTableBySorting(pointsTables);
            matchPointsCalculated.put(matchId, true);
            return ResponseEntity.ok("Points Calculated");
        }else{
            return ResponseEntity.badRequest().body("Points for this match have been calculated already!!!!");
        }
    }

    private void savePointsTableBySorting(List<PointsTable> pointsTables) {
        Collections.sort(pointsTables,new PointsTableComparator());

        tableRepository.saveAll(pointsTables);
    }

    private double calculateNetRunRate(Score a, Score b) {
        int runsScored = a.getRuns();
        int runsConceded = b.getRuns();
        double oversPlayedT1 = a.getOversPlayed();
        double oversPlayedT2 = b.getOversPlayed();

        return (runsScored/oversPlayedT1)-(runsConceded/oversPlayedT2);
    }

}
