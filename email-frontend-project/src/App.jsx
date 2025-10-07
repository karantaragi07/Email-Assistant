import React, { useState } from "react";
import "./App.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import CircularProgress from "@mui/material/CircularProgress";
import axios from "axios";

function App() {
  const [mailContent, setMailContent] = useState("");
  const [tone, setTone] = useState("");
  const [generatedReply, setGeneratedReply] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        "http://localhost:8082/api/mail/generate",
        { mailContent, tone }
      );

      setGeneratedReply(
        typeof response.data === "string"
          ? response.data
          : response.data.reply || JSON.stringify(response.data)
      );
    } catch (error) {
      console.error(
        "Error generating reply:",
        error.response ? error.response.data : error.message
      );
      setGeneratedReply("⚠ Error generating reply. See console.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Typography variant="h3" component="h1" gutterBottom>
        Email Reply Generator
      </Typography>

      <Box sx={{ py: 3 }}>
        <TextField
          fullWidth
          multiline
          rows={6}
          variant="outlined"
          label="Original Mail Content"
          value={mailContent || ""}
          onChange={(e) => setMailContent(e.target.value)}
          sx={{ mb: 2 }}
        />

        <FormControl fullWidth sx={{ mb: 2 }}>
          {/* ✅ Proper labelId linking */}
          <InputLabel id="tone-label">Tone (Optional)</InputLabel>
          <Select
            labelId="tone-label"
            value={tone || ""}
            label="Tone (Optional)"
            onChange={(e) => setTone(e.target.value)}
          >
            <MenuItem value="">None</MenuItem>
            <MenuItem value="professional">Professional</MenuItem>
            <MenuItem value="friendly">Friendly</MenuItem>
            <MenuItem value="strict">Strict</MenuItem>
            <MenuItem value="brotherhood">Brotherhood</MenuItem>
          </Select>
        </FormControl>

        <Button
          variant="contained"
          sx={{ mb: 2 }}
          onClick={handleSubmit}
          disabled={!mailContent || loading}
        >
          {loading ? (
            <CircularProgress size={24} color="inherit" />
          ) : (
            "Generate Reply"
          )}
        </Button>
      </Box>

      <Box sx={{ py: 3 }}>
        <TextField
          fullWidth
          multiline
          rows={6}
          variant="outlined"
          value={generatedReply || ""}
          // ✅ Correct prop name
          InputProps={{ readOnly: true }}
          sx={{ mb: 2 }}
        />
        <Button
          variant="outlined"
          disabled={!generatedReply}
          onClick={() => navigator.clipboard.writeText(generatedReply)}
        >
          Copy to Clipboard!!
        </Button>
      </Box>
    </Container>
  );
}

export default App;
