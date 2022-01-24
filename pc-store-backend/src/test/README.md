## Testing

### addCandidateMultipart

`curl -X POST "http://localhost:8080/addCandidateMultipart" -H "Content-Type: multipart/form-data" -F "candidate={\"name\":\"Vorname\",\"surname\":\"Nachname\",\"candidateFiles\":[{\"id\":\"1\",\"name\":\"name\",\"notes\":\"notes\",\"type\":\"type\"}]}" -F "1=@pom.xml"`

### download

`curl "http://localhost:8080/download/internal-order-file/3fa49708-d0c6-4612-8f09-600d4412ee9c"`