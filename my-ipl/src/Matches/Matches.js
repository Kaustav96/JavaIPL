import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Matches.css'; // Import CSS file

const Matches = () => {
  const [dateTime, setDateTime] = useState('');
  const [team1Name, setTeam1Name] = useState('');
  const [team2Name, setTeam2Name] = useState('');
  const [teams, setTeams] = useState([]); // State variable to store teams

  useEffect(() => {
    // Fetch teams data from backend API and set them in state
    const fetchTeams = async () => {
      try {
        const response = await axios.get('http://localhost:8080/v1/teams');
        const teamsData = response.data;
        setTeams(teamsData);
        // Optionally, you can set the teams in state if you want to display them in the form
        console.log('Teams:', teamsData);
      } catch (error) {
        console.error('Error fetching teams:', error);
      }
    };
    fetchTeams();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Look up team IDs based on team names
      const team1 = teams.find(team => team.teamName === team1Name);
      const team2 = teams.find(team => team.teamName === team2Name);

      // Create request body with team IDs
      const requestBody = {
        dateTime: dateTime,
        team1: {
          id: team1.id
        },
        team2: {
          id: team2.id
        }
      };

      // Send request to add match
      const response = await axios.post('http://localhost:8080/v1/matches', requestBody);
      if(response.data !=null ){
        console.log('Match added successfully:', response.data);
        setDateTime('');
        setTeam1Name('');
        setTeam2Name('');
      }
      
      // Optionally, you can reset the form fields here
    } catch (error) {
      console.error('Error adding match:', error);
    }
  };

  return (
    <div className="matches-form-container"> {/* Apply CSS class to container */}
      <h2>Add Match</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group"> {/* Apply CSS class to form group */}
          <label>Date Time:</label>
          <input type="datetime-local" value={dateTime} onChange={(e) => setDateTime(e.target.value)} required/>
        </div>
        <div className="form-group">
          <label>Team 1 Name:</label>
          <input type="text" value={team1Name} onChange={(e) => setTeam1Name(e.target.value)} required />
        </div>
        <div className="form-group">
          <label>Team 2 Name:</label>
          <input type="text" value={team2Name} onChange={(e) => setTeam2Name(e.target.value)} required/>
        </div>
        <button type="submit" className="submit-btn">Submit</button> {/* Apply CSS class to button */}
      </form>
    </div>
  );
};

export default Matches;
