import { Button, Card, CardContent, Stack, TextField, Typography } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import { useState } from 'react';
import { api } from '../services/api';

export function AssistantPage() {
  const [message, setMessage] = useState('Create release plan for project Alpha');
  const [conversation, setConversation] = useState<string[]>([]);
  async function send() {
    const { data } = await api.post('/chat', { message });
    setConversation([...conversation, `You: ${message}`, data.answer]);
    setMessage('');
  }
  return (
    <Stack spacing={3}>
      <Typography variant="h4">AI Assistant</Typography>
      <Card sx={{ minHeight: 460 }}><CardContent><Stack spacing={2}>
        {conversation.map((item, index) => <Typography key={index} sx={{ whiteSpace: 'pre-wrap' }}>{item}</Typography>)}
      </Stack></CardContent></Card>
      <Stack direction="row" spacing={1}>
        <TextField fullWidth value={message} onChange={(e) => setMessage(e.target.value)} placeholder="Ask NexusOps to plan, analyze, search, or report" />
        <Button endIcon={<SendIcon />} onClick={send}>Send</Button>
      </Stack>
    </Stack>
  );
}
