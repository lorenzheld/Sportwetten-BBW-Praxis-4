// components/Register.jsx
import React, { useState } from 'react';
import { api } from '../services/api';

export default function Register() {
    const [formData, setFormData] = useState({
        username: '',
        password: ''
    });
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const newUser = await api.createUser(formData);
            localStorage.setItem('userId', newUser.id);
            // Optional: Weiterleitung zur Account-Seite
            window.location.href = '/account';
        } catch (err) {
            setError('Fehler bei der Registrierung');
            console.error(err);
        }
    };

    return (
        <div style={styles.container}>
            <h2>Registrierung</h2>
            {error && <div style={styles.error}>{error}</div>}
            <form onSubmit={handleSubmit}>
                <div style={styles.inputGroup}>
                    <label>Benutzername:</label>
                    <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div style={styles.inputGroup}>
                    <label>Passwort:</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <md-filled-button type="submit">Registrieren</md-filled-button>
            </form>
        </div>
    );
}

const styles = {
    container: {
        maxWidth: '400px',
        margin: '2rem auto',
        padding: '1rem'
    },
    inputGroup: {
        marginBottom: '1rem'
    },
    error: {
        color: 'red',
        marginBottom: '1rem'
    }
};