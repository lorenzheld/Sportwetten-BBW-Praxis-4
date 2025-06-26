import '@material/web/button/filled-button.js';
import '@material/web/icon/icon.js';
import React from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import './App.css';

import NewBet from './components/NewBet.jsx';
import Leaderboard from './components/Leaderboard.jsx';
import MyBets from './components/MyBets.jsx';
import Account from './components/Account.jsx';

function Navbar() {
  const navigate = useNavigate();

  return (
      <div style={styles.navbar}>
        <div style={styles.navLeft}>
          <md-filled-button onClick={() => navigate('/')}>Neue Wette</md-filled-button>
          <md-filled-button onClick={() => navigate('/leaderboard')}>Rangliste</md-filled-button>
          <md-filled-button onClick={() => navigate('/mybets')}>Meine Wetten</md-filled-button>
        </div>

        <div style={styles.navRight}>
          <md-filled-button onClick={() => navigate('/account')}>
            <md-icon>account_circle</md-icon>
          </md-filled-button>

          <span style={styles.coinText}>500</span>
          <md-icon>attach_money</md-icon>
        </div>
      </div>
  );
}

export default function App() {
  return (
      <Router>
        <Navbar />
        <div style={styles.content}>
          <Routes>
            <Route path="/" element={<NewBet />} />
            <Route path="/leaderboard" element={<Leaderboard />} />
            <Route path="/mybets" element={<MyBets />} />
            <Route path="/account" element={<Account />} />
          </Routes>
        </div>
      </Router>
  );
}

const styles = {
  navbar: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '1rem',
    gap: '1rem',
    backgroundColor: '#f5f5f5',
    borderBottom: '1px solid #ddd',
  },
  navLeft: {
    display: 'flex',
    gap: '1rem',
  },
  navRight: {
    display: 'flex',
    alignItems: 'center',
    gap: '0.5rem',
  },
  coinText: {
    fontWeight: 'bold',
    fontSize: '1rem',
  },
  content: {
    padding: '1rem',
  },
};
