import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Score.css'; // Import CSS file

const Score = () => {
  const [homeTeamRuns, setHomeTeamRuns] = useState('');
  const [homeTeamWickets, setHomeTeamWickets] = useState('');
  const [homeTeamOvers, setHomeTeamOvers] = useState('');
  const [awayTeamRuns, setAwayTeamRuns] = useState('');
  const [awayTeamWickets, setAwayTeamWickets] = useState('');
  const [awayTeamOvers, setAwayTeamOvers] = useState('');
  const [matchId, setMatchId] = useState('');
  const [matchDetails, setMatchDetails] = useState(null);
  const [homeTeamId, setHomeTeamId] = useState(null);
  const [awayTeamId, setAwayTeamId] = useState(null);
  const [homeTeamName, setHomeTeamName] = useState('');
  const [awayTeamName, setAwayTeamName] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    if(matchId){
    const fetchMatchDetails = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/v1/matches/${matchId}`);
      setMatchDetails(response.data);
      setErrorMessage('');
      setAwayTeamName(response.data.team2.teamName)
      setHomeTeamName(response.data.team1.teamName)
      setHomeTeamId(response.data.team1.id)
      setAwayTeamId(response.data.team2.id)
      // fetch score data for the given match id
      const scoreResponse = await axios.get(`http://localhost:8080/v1/scores/${matchId}`);
      if(scoreResponse.data && scoreResponse.data.length>0){
        // console.log(scoreResponse.data)
        const homeTeamScore = scoreResponse.data.find(score => score.team_id === homeTeamId);
        const awayTeamScore = scoreResponse.data.find(score => score.team_id === awayTeamId);
        console.log(homeTeamScore);
        console.log(awayTeamScore);
        if(homeTeamScore){
          setHomeTeamOvers(homeTeamScore.oversPlayed);
          setHomeTeamRuns(homeTeamScore.runs);
          setHomeTeamWickets(homeTeamScore.wickets);
        }
        if(awayTeamScore){
          setAwayTeamOvers(awayTeamScore.oversPlayed);
          setAwayTeamRuns(awayTeamScore.runs);
          setAwayTeamWickets(awayTeamScore.wickets);
        }
      }else{
        // console.log("not found")
        setHomeTeamOvers('');
        setHomeTeamRuns('');
        setHomeTeamWickets('');
        setAwayTeamOvers('');
        setAwayTeamRuns('');
        setAwayTeamWickets('');
      }
    } catch (error) {
      setMatchDetails(null);
      setErrorMessage('Match not found');
    }
    
  };
    fetchMatchDetails();
  }
}, [matchId, homeTeamId, awayTeamId]);

  const handleInputChange = (event) => {
    setMatchId(event.target.value);
  };
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Create request body
      const requestBody1 = {
        runs: parseInt(homeTeamRuns),
        wickets: parseInt(homeTeamWickets),
        oversPlayed: parseFloat(homeTeamOvers),
        match: {
          id: parseInt(matchId)
        },
        team_id:parseInt(homeTeamId)
      };
      const requestBody2 = {
        runs: parseInt(awayTeamRuns),
        wickets: parseInt(awayTeamWickets),
        oversPlayed: parseFloat(awayTeamOvers),
        match: {
          id: parseInt(matchId)
        },
        team_id: parseInt(awayTeamId)
      }
      console.log(requestBody1)
      console.log(requestBody2)
      // Send request to add score
      const response1 = await axios.post('http://localhost:8080/v1/scores', requestBody1);
      const response2 = await axios.post('http://localhost:8080/v1/scores', requestBody2);
      console.log('Home team score added successfully:', response1.data);
      console.log('Away team score added successfully:', response2.data);

      // Call API to calculate points based on match ID
      const pointsResponse = await axios.get(`http://localhost:8080/v1/scores/calculatePoints/${matchId}`);
      console.log('Points calculated successfully:', pointsResponse.data);
      // Optionally, you can reset the form fields here
    } catch (error) {
      console.error('Error adding score:', error);
    }
  };

  return (
    <div className="score-form-container">
      <h2>Add Score</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Match ID:</label>
          <input type="text" value={matchId} onChange={handleInputChange} />
        </div>
        {/* <button type="button" className="submit-button" onClick={fetchMatchDetails}>Get Match Details</button> */}
        {matchId && (
          <>
          {errorMessage && <p>{errorMessage}</p>}
          {matchDetails && (
          <>
            <div className="form-group">
              <label>Home Team Name:</label>
              <input type="text" value={homeTeamName} readOnly />
            </div>
            <div className="form-group"> 
              <label>Home Team Runs:</label>
              <input required type="number" value={homeTeamRuns} onChange={(e) => setHomeTeamRuns(e.target.value)} />
            </div>
            <div className="form-group"> 
              <label>Home Team Wickets:</label>
              <input required type="number" value={homeTeamWickets} onChange={(e) => setHomeTeamWickets(e.target.value)} />
            </div>
            <div className="form-group">
              <label>Home Team Overs Faced:</label>
              <input required type="number" value={homeTeamOvers} onChange={(e) => setHomeTeamOvers(e.target.value)} />
            </div>
            <div className="form-group">
                  <label>Away Team Name:</label>
                  <input type="text" value={awayTeamName} readOnly />
                </div>
            <div className="form-group">
              <label>Away Team Runs:</label>
              <input type="number" required value={awayTeamRuns} onChange={(e) => setAwayTeamRuns(e.target.value)} />
            </div>
            <div className="form-group"> 
              <label>Away Team Wickets:</label>
              <input required type="number" value={awayTeamWickets} onChange={(e) => setAwayTeamWickets(e.target.value)} />
            </div>
            <div className="form-group">
              <label>Away Team Overs Faced:</label>
              <input type="number" required value={awayTeamOvers} onChange={(e) => setAwayTeamOvers(e.target.value)} />
            </div>
            </>
        )}
        <button type="submit" className="submit-button">Submit</button> {/* Apply CSS class to button */}
        </>
        )}
      </form>
      
    </div>
  );
};

export default Score;
