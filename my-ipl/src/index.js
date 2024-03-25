import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Matches from './Matches/Matches';
import PointsTable from './PointsTable/PointsTable';
import reportWebVitals from './reportWebVitals';
import Score from './Score/Score';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
   <div className="container">
      <div className="component">
        <App />
      </div>
      <div className="component">
        <Matches />
      </div>
      <div className="component">
        <Score />
      </div>
    </div>
    <div className="component">
        <PointsTable />
      </div>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
