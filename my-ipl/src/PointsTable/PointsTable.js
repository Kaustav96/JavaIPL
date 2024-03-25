import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './PointsTable.css'; // Import the CSS file

const PointsTable = () => {
  const [pointsTable, setPointsTable] = useState([]);

  useEffect(() => {
    fetchPointsTable();
  }, []);

  const fetchPointsTable = async () => {
    try {
      const response = await axios.get('http://localhost:8080/v1/table'); // Replace with your backend API endpoint
      const sortedPointsTable = sortPointsTable(response.data);
      setPointsTable(sortedPointsTable);
    } catch (error) {
      console.error('Error fetching points table:', error);
    }
  };

  const sortPointsTable = (data) => {
    // Sort based on points (descending)
    data.sort((a, b) => b.pts - a.pts || b.nrr - a.nrr);
    return data;
  };

  return (
    <div className='points-table-container'>
      <h2>Points Table</h2>
      <table className='points-table'>
        <thead>
          <tr>
            <th>Team</th>
            <th>Matches Played</th>
            <th>Wins</th>
            <th>Losses</th>
            <th>Tied</th>
            <th>No Results</th>
            <th>Points</th>
            <th>Net Run Rate</th>
          </tr>
        </thead>
        <tbody>
          {pointsTable.map((team) => (
            <tr key={team.id}>
              <td>{team.teamName}</td>
              <td>{team.matches}</td>
              <td>{team.won}</td>
              <td>{team.lost}</td>
              <td>{team.tied}</td>
              <td>{team.nr}</td>
              <td>{team.pts}</td>
              <td>{team.nrr}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PointsTable;
