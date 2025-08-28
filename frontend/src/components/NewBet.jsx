import { useState, useMemo } from "react";
import "@material/web/button/filled-button.js";
import "@material/web/icon/icon.js";
import "@material/web/iconbutton/filled-icon-button.js";
import "@material/web/divider/divider.js";
import "@material/web/textfield/filled-text-field.js";

/**
 * Komponente "Neue Wette" – Datumseingabe, Laden & Wett-Button pro Match
 * ----------------------------------------------------------------------
 * Eingabeformat im Textfeld:
 *   • „TT.MM“  (z. B. 26.05  → aktuelles Jahr)
 *   • „YYYY-MM-DD“ (ISO)
 *
 * Beim Laden wird der gewählte Tag **plus Folgetag** geholt.
 * Rechts neben jedem Spiel erscheint ein <md-filled-icon-button> mit
 * "payments"‑Icon.  Klick → Konsolen‑Log (Platzhalter für Routing).
 *
 * Abhängigkeit:  npm install @material/web
 */

export default function NewBet() {
    const todayIso = new Date().toISOString().slice(0, 10);
    const todayDisplay = `${todayIso.slice(8, 10)}.${todayIso.slice(5, 7)}`;

    const [inputDate, setInputDate] = useState(todayDisplay);
    const [matches, setMatches] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const parseInput = (str) => {
        const isoRegex = /^\d{4}-\d{2}-\d{2}$/;
        const dmRegex = /^(\d{2})\.(\d{2})$/;
        if (isoRegex.test(str)) return str;
        const dm = str.match(dmRegex);
        if (dm) {
            const [_, dd, mm] = dm;
            const yyyy = new Date().getFullYear();
            return `${yyyy}-${mm}-${dd}`;
        }
        return null;
    };

    const fetchMatches = async () => {
        const isoFrom = parseInput(inputDate.trim());
        if (!isoFrom) {
            setError("Bitte Datum im Format TT.MM oder YYYY-MM-DD eingeben.");
            setMatches([]);
            return;
        }

        const fromDate = new Date(`${isoFrom}T00:00:00Z`);
        const isoTo = new Date(fromDate.getTime() + 86400000)
            .toISOString()
            .slice(0, 10);

        const url = `/api/matches?dateFrom=${isoFrom}&dateTo=${isoTo}`;

        try {
            const res = await fetch(url);
            if (!res.ok) throw new Error(`HTTP ${res.status}`);
            const data = await res.json();
            setMatches(data.matches ?? []);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    // Handler für Wett-Button
    const handleBet = (match) => {
        console.log(
            `🔔 Wette angeklickt: ${match.homeTeam.shortName} vs ${match.awayTeam.shortName} (ID ${match.id})`
        );
        // TODO: mit Router auf Detailseite navigieren, z. B. navigate(`/bet/${match.id}`)
    };

    // sortiert
    const sorted = useMemo(
        () =>
            [...matches].sort(
                (a, b) => new Date(a.utcDate) - new Date(b.utcDate)
            ),
        [matches]
    );

    const timeString = (iso) =>
        new Date(iso).toLocaleTimeString("de-DE", {
            hour: "2-digit",
            minute: "2-digit",
            timeZone: "Europe/Zurich",
        });

    const dayMonth = (iso) =>
        new Date(iso).toLocaleDateString("de-DE", { day: "2-digit", month: "2-digit" });

    const statusLabel = (m) => {
        switch (m.status) {
            case "FINISHED":
                return "FT";
            case "IN_PLAY":
            case "PAUSED":
                return "LIVE";
            case "TIMED":
                return timeString(m.utcDate);
            default:
                return m.status;
        }
    };

    const scoreLine = (m) => {
        const { home, away } = m.score.fullTime || {};
        return home == null || away == null ? "-" : `${home} – ${away}`;
    };

    return (
        <div style={{ maxWidth: 760, margin: "0 auto", padding: "1rem" }}>
            <h2 style={{ fontWeight: 700, marginBottom: "1rem" }}>Neue Wette</h2>

            {/* Datumseingabe + Laden */}
            <div
                style={{ display: "flex", gap: "1rem", alignItems: "flex-end", marginBottom: "1rem" }}
            >
                <md-filled-text-field
                    label="Datum (TT.MM)"
                    value={inputDate}
                    onInput={(e) => setInputDate(e.target.value)}
                    onkeydown={(e) => e.key === "Enter" && fetchMatches()}
                    supporting-text="Enter oder Button drücken"
                    style={{ maxWidth: 150 }}
                ></md-filled-text-field>

                <md-filled-button onClick={fetchMatches} disabled={loading}>
                    {loading ? "Lädt…" : "Laden"}
                </md-filled-button>
            </div>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {sorted.length > 0 && (
                <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>
                    {sorted.map((m, idx) => (
                        <li key={m.id} style={{ padding: "0.75rem 0" }}>
                            <div
                                style={{ display: "flex", alignItems: "center", gap: "0.5rem" }}
                            >
                                {/* Status */}
                                <md-filled-button
                                    disabled={statusLabel(m) !== "LIVE"}
                                    style={{ pointerEvents: "none", width: 68 }}
                                >
                                    {statusLabel(m)}
                                </md-filled-button>

                                {/* Datum */}
                                <span style={{ width: 50, textAlign: "center", fontSize: 12, color: "#666" }}>
                  {dayMonth(m.utcDate)}
                </span>

                                {/* Heimteam */}
                                <img src={m.homeTeam.crest} alt={m.homeTeam.shortName} style={{ height: 24, width: 24 }} />
                                <span
                                    style={{ flex: 1, overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap" }}
                                >
                  {m.homeTeam.shortName}
                </span>

                                {/* Score */}
                                <span style={{ minWidth: 64, textAlign: "center", fontWeight: 600 }}>
                  {scoreLine(m)}
                </span>

                                {/* Auswärtsteam */}
                                <span
                                    style={{ flex: 1, textAlign: "right", overflow: "hidden", textOverflow: "ellipsis", whiteSpace: "nowrap" }}
                                >
                  {m.awayTeam.shortName}
                </span>
                                <img src={m.awayTeam.crest} alt={m.awayTeam.shortName} style={{ height: 24, width: 24 }} />

                                {/* Wett-Button */}
                                <md-filled-icon-button
                                    style={{ marginLeft: 8 }}
                                    aria-label="Wetten"
                                    title="Auf dieses Spiel wetten"
                                    onClick={() => handleBet(m)}
                                >
                                    <md-icon slot="icon">payments</md-icon>
                                </md-filled-icon-button>
                            </div>

                            {idx < sorted.length - 1 && <md-divider style={{ marginTop: 12 }}></md-divider>}
                        </li>
                    ))}
                </ul>
            )}

            {!loading && !error && sorted.length === 0 && (
                <p style={{ color: "#666" }}>Keine Spiele gefunden.</p>
            )}
        </div>
    );
}
