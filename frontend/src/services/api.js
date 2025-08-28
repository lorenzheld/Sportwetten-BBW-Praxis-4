const API_URL = import.meta.env.VITE_BACKEND_URL;

export const api = {
  // Benutzer-bezogene Anfragen
  async login(credentials) {
    const response = await fetch(`${API_URL}/api/users/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials)
    });
    return response.json();
  },

  // Wetten-bezogene Anfragen
  async getUserBets(userId) {
    const response = await fetch(`${API_URL}/api/bets/user/${userId}`);
    return response.json();
  },

  async placeBet(betData) {
    const response = await fetch(`${API_URL}/api/bets/save`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(betData)
    });
    return response.json();
  },

  // Benutzer-bezogene Anfragen
  async getUser(userId) {
    const response = await fetch(`${API_URL}/api/users/${userId}`);
    return response.json();
  },

  createUser: async (formData) => {
    const response = await fetch(`${API_URL}/api/users`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData)
    });
    if (!response.ok) {
      throw new Error('Registrierung fehlgeschlagen');
    }
    return response.json();
  }
};