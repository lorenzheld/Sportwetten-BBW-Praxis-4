// components/Account.jsx
import '@material/web/button/filled-button.js';
import '@material/web/icon/icon.js';
import '@material/web/divider/divider.js';
import React from 'react';

export default function Account() {
    const user = {
        username: 'Lorenz35',
        balance: 500,
        email: 'lolo@example.com',
    };

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
                        <td style={styles.iconCell}><md-icon>email</md-icon></td>
                        <td style={styles.label}>E-Mail:</td>
                        <td style={styles.value}>{user.email}</td>
                    </tr>
                    <tr>
                        <td style={styles.iconCell}><md-icon>attach_money</md-icon></td>
                        <td style={styles.label}>Kontostand:</td>
                        <td style={styles.value}>{user.balance} Coins</td>
                    </tr>
                    </tbody>
                </table>

                <md-divider></md-divider>

                <md-filled-button style={{ marginTop: '1rem' }}>
                    Einstellungen öffnen
                </md-filled-button>
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
