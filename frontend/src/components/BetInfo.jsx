import React, { useState, useEffect } from 'react';

export default function BetInfo() {
    const [emblem, setEmblem] = useState('');
    const [awayTeam, setAwayTeam] = useState('');
    const [homeTeam, setHomeTeam] = useState('');
    const [awayCrest, setAwayCrest] = useState('');
    const [homeCrest, setHomeCrest] = useState('');

    useEffect(() => {
        const fetchMatch = async () => {
            const matchId = localStorage.getItem('matchId');
            const url = `/api/matches/${matchId}`;
            try {
                const res = await fetch(url);
                const data = await res.json();
                setEmblem(data.competition.emblem);
                setAwayTeam(data.awayTeam.shortName);
                setHomeTeam(data.homeTeam.shortName);
                setAwayCrest(data.awayTeam.crest);
                setHomeCrest(data.homeTeam.crest);
            } catch (err) {
                console.error(err);
            }
        };
        fetchMatch();
    }, []);

    const containerStyle = {
        background: '#fff',
        borderRadius: '12px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.08)',
        padding: '24px',
        maxWidth: '400px',
        margin: '32px auto',
        textAlign: 'center'
    };

    const emblemStyle = {
        marginBottom: '16px'
    };

    const competitionEmblemStyle = {
        width: '80px',
        height: '80px',
        objectFit: 'contain'
    };

    const matchStyle = {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        gap: '16px'
    };

    const teamButtonStyle = {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: '12px 16px',
        borderRadius: '8px',
        border: '1px solid #eee',
        background: '#f9f9f9',
        cursor: 'pointer',
        minWidth: '100px'
    };

    const teamCrestStyle = {
        width: '48px',
        height: '48px',
        objectFit: 'contain',
        marginBottom: '8px'
    };

    const teamNameStyle = {
        fontSize: '1rem',
        fontWeight: 600,
        color: '#222',
        margin: 0
    };

    const vsStyle = {
        color: '#888',
        fontWeight: 400,
        margin: '0 8px'
    };

    return (
        <div style={containerStyle}>
            <div style={emblemStyle}>
                {emblem && <img src={emblem} alt="Emblem" style={competitionEmblemStyle} />}
            </div>
            <div style={matchStyle}>
                <button style={teamButtonStyle}>
                    <img src={homeCrest} alt="Heimteam" style={teamCrestStyle} />
                    <span style={teamNameStyle}>{homeTeam}</span>
                </button>
                <span style={vsStyle}>vs</span>
                <button style={teamButtonStyle}>
                    <img src={awayCrest} alt="Auswärtsteam" style={teamCrestStyle} />
                    <span style={teamNameStyle}>{awayTeam}</span>
                </button>
            </div>
        </div>
    );
}