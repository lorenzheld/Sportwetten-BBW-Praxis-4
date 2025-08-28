// components/Account.jsx
import '@material/web/button/filled-button.js';
import '@material/web/icon/icon.js';
import '@material/web/divider/divider.js';
import React from 'react';
import { useState, useEffect } from 'react';
import { api } from '../services/api';

export default function Account() {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                // Angenommen, die User-ID ist im localStorage gespeichert
                const userId = localStorage.getItem('userId');
                const userData = await api.getUser(userId);
                setUser(userData);
            } catch (err) {
                setError('Fehler beim Laden der Benutzerdaten');
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchUserData();
    }, []);

    if (loading) return <div>Lade...</div>;
    if (error) return <div>{error}</div>;
    if (!user) return <div>Kein Benutzer gefunden</div>;

    return (
        <div style={styles.container}>
            <h2 style={styles.heading}>Mein Konto</h2>
            <section style={styles.infoBox}>
                <table style={styles.table}>
                    <tbody>
                    <tr>
                        <td style={styles.iconCell}><md-icon>person</md-icon></td>
                        <td style={styles.label}>Benutzername:</td>
                        <td style={styles.value}>{user.username}</td>
                    </tr>
                    <tr>
                        <td style={styles.iconCell}><md-icon>attach_money</md-icon></td>
                        <td style={styles.label}>Kontostand:</td>
                        <td style={styles.value}>{user.balance}</td>
                    </tr>
                    </tbody>
                </table>
            </section>
        </div>
    );
}

const styles = {
    container: {
        maxWidth: '500px',
        margin: '2rem auto',
        padding: '1rem',
    },
    heading: {
        fontSize: '1.8rem',
        marginBottom: '1rem',
        textAlign: 'center',
    },
    infoBox: {
        padding: '1.5rem',
        borderRadius: '12px',
        backgroundColor: '#f4efe9', // natürliche Farbe
        border: '2px solid #d8cfc4',
        boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
    },
    table: {
        width: '100%',
        borderCollapse: 'collapse',
        marginBottom: '1rem',
    },
    iconCell: {
        width: '2rem',
        textAlign: 'center',
        verticalAlign: 'top',
        paddingRight: '0.5rem',
    },
    label: {
        fontWeight: '500',
        color: 'var(--md-sys-color-on-surface-variant)',
        verticalAlign: 'top',
        paddingRight: '0.5rem',
        whiteSpace: 'nowrap',
    },
    value: {
        fontWeight: '600',
        verticalAlign: 'top',
    },
};
