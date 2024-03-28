// import logo from './logo.svg';
import './App.css';
// import axios from 'axios';
import PointsTable from './PointsTable/PointsTable';
import React,{useState, useEffect} from 'react';
function App() {
  // State to store form data
  const [teamName, setTeamName] = useState('');
  const [teamCount, setTeamCount] = useState(0);

  // Function to handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();

    // Prepare data for the backend API
    const teamData = {
      teamName: teamName
    };

    try {
      // Make a POST request to your Java backend API
      const response = await fetch('http://localhost:8080/v1/teams', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(teamData)
      });

      if (response.ok) {
        // Team saved successfully
        console.log('Team saved successfully');
        // Reset form fields
        setTeamName('');
        setTeamCount(teamCount+1);
      } else {
        // Handle error response
        console.error('Failed to save team');
      }
    } catch (error) {
      // Handle network errors
      console.error('Network error:', error);
    }
  };
  useEffect(() => {
    // Get team count from local storage
    const savedTeamCount = localStorage.getItem('teamCount');
    if (savedTeamCount) {
      setTeamCount(parseInt(savedTeamCount));
    }
  
    if (teamCount >= 10) {
      // Disable form input
      document.getElementById('teamName').disabled = true;
      document.getElementById('submitButton').disabled = true;
    }
  }, []);
  
  useEffect(() => {
    // Store team count in local storage
    localStorage.setItem('teamCount', teamCount.toString());
    
    if (teamCount >= 10) {
      // Disable form input
      document.getElementById('teamName').disabled = true;
      document.getElementById('submitButton').disabled = true;
    }
  }, [teamCount]);
  return (
    <div className="form-container">
      <h2 className="form-heading">Add New Team</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="teamName" className="form-label">Team Name:</label>
          <input
            type="text"
            id="teamName"
            value={teamName}
            onChange={(e) => setTeamName(e.target.value)}
            className="form-input"
            required
          />
        </div>
        <button type="submit" id="submitButton" className="submit-btn">Submit</button>
      </form>
    </div>
  );
}

export default App;
