import express from "express";
import sqlite3 from "sqlite3";
import cors from "cors";

const app = express();
app.use(cors());
app.use(express.json());

const db = new sqlite3.Database("codons.db"); // database is in the same folder

// Test DB on startup
db.all("SELECT name FROM sqlite_master WHERE type='table';", (err, rows) => {
  if (err) console.error("DB Error:", err);
  else console.log("Tables in DB:", rows);
});

// Get single codon info
app.get("/codon/:codon", (req, res) => {
  const codon = req.params.codon.toUpperCase();
  db.get("SELECT * FROM codon_table WHERE codon = ?", [codon], (err, row) => {
    if (err) return res.status(500).json({ error: err.message });
    if (!row) return res.status(404).json({ error: "Codon not found" });
    res.json(row);
  });
});

// Translate mRNA sequence
app.post("/translate", (req, res) => {
  const { mrna } = req.body;
  if (!mrna) return res.status(400).json({ error: "Missing mRNA sequence" });

  const seq = mrna.toUpperCase().replace(/[^AUCG]/g, "");
  const result = [];
  let pending = Math.ceil(seq.length / 3);

  for (let i = 0; i < seq.length; i += 3) {
    const codon = seq.substring(i, i + 3);
    db.get("SELECT * FROM codon_table WHERE codon = ?", [codon], (err, row) => {
      if (err) result.push({ codon, error: err.message });
      else if (!row) result.push({ codon, error: "Unknown codon" });
      else result.push(row);
      pending--;
      if (pending === 0) res.json(result);
    });
  }
});

const PORT = 3000;
app.listen(PORT, () => console.log(`Server running on http://localhost:${PORT}`));
