import { Button, Card, CardContent, Grid, Stack, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { api } from '../services/api';

export function KnowledgePage() {
  const [doc, setDoc] = useState({ title: '', sourceType: 'MD', content: '' });
  const [query, setQuery] = useState('');
  const [answer, setAnswer] = useState('');
  async function upload() { await api.post('/documents/upload', doc); setDoc({ title: '', sourceType: 'MD', content: '' }); }
  async function search() { setAnswer((await api.post('/documents/search', { query })).data.answer); }
  return (
    <Stack spacing={3}>
      <Typography variant="h4">Knowledge Base</Typography>
      <Grid container spacing={2}>
        <Grid item xs={12} md={6}><Card><CardContent><Stack spacing={2}>
          <Typography variant="h6">Upload Document</Typography>
          <TextField label="Title" value={doc.title} onChange={(e) => setDoc({ ...doc, title: e.target.value })} />
          <TextField label="Content" multiline minRows={8} value={doc.content} onChange={(e) => setDoc({ ...doc, content: e.target.value })} />
          <Button onClick={upload}>Upload</Button>
        </Stack></CardContent></Card></Grid>
        <Grid item xs={12} md={6}><Card><CardContent><Stack spacing={2}>
          <Typography variant="h6">Semantic Search</Typography>
          <TextField label="Question" value={query} onChange={(e) => setQuery(e.target.value)} />
          <Button onClick={search}>Ask Knowledge Agent</Button>
          <Typography>{answer}</Typography>
        </Stack></CardContent></Card></Grid>
      </Grid>
    </Stack>
  );
}
