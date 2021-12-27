## Testing

### addCandidateMultipart

`curl -X POST "http://localhost:8080/addCandidateMultipart" -H "Content-Type: multipart/form-data" -F "candidate={\"name\":\"Vorname\",\"surname\":\"Nachname\",\"candidateFiles\":[{\"id\":\"1\",\"name\":\"name\",\"notes\":\"notes\",\"type\":\"type\"}]}" -F "1=@pom.xml"`

### download

`curl "http://localhost:8080/download/candidate-file/42764ce5-900e-4ba8-9235-6ca78607b199"`