## Testing

### Process



### addCandidateMultipart

`curl -X POST "http://localhost:8080/internal-order-multipart" -H "Content-Type: multipart/form-data" -F "internal-order={ \"clientData\": { \"salutation\": \"male\", \"name\": \"John\", \"surname\": \"Smith\", \"street\": \"Hauptstraße\", \"houseNumber\": \"110\", \"zip\": 90459, \"city\": \"Nürnberg\", \"telephone\": \"+49528252826\", \"cellphone\": \"+49528252826\", \"email\": \"example@test.de\" }, \"personalComputer\": { \"computerCase\": \"MSI MAG Forge 100R\", \"motherboard\": \"Gigabyte B550 Aorus Pro V2\", \"processor\": \"AMD Ryzen 7 5800X\", \"graphicsCard\": \"AMD Radeon RX 6700 XT 12 GB\", \"randomAccessMemory\": \"32GB Corsair Vengeance LPX DDR4-3000\", \"storageDevice\": \"250GB Samsung 870 EVO\", \"powerSupplyUnit\": \"700W - be quiet! Pure power 11\" }, \"privacyPolicy\": true, \"files\":[{ \"id\":\"1\",\"name\":\"pom.xml\" }] }" -F "1=@pom.xml"`

### download

`curl "http://localhost:8080/download/internal-order-file/3fa49708-d0c6-4612-8f09-600d4412ee9c"`

### JWT auth
curl -X POST http://localhost:8080/get-token -H "Content-Type: application/json" -d '{"password": "randomuser123","username": "password"}' -v

### Logout

http://localhost:8180/realms/master/protocol/openid-connect/logout



curl -X POST http://localhost:8180/realms/master/protocol/openid-connect/logout -H "Content-Type: application/x-www-form-urlencoded" -d "client_id=test-client" -H "Authorization: Bearer "

-d "refresh_token=<refresh_token>"


client_id=<my_client_id>&refresh_token=<refresh_token>
---
curl "http://localhost:8180/admin/realms/master/sessions/3289b270-7bf8-4a4b-b65b-8110df898161" -X DELETE
-H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/109.0"
-H "Accept: application/json, text/plain, */*"
-H "Accept-Language: de,en-US;q=0.7,en;q=0.3" 
-H "Accept-Encoding: gzip, deflate, br"
-H "Authorization: bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ6RkFDenIwbERFajMwZHJvT3l2VVdJMUZVblExQ0N3UTMzX2JoOTB3QlVjIn0.eyJleHAiOjE2NzUxODg5NTcsImlhdCI6MTY3NTE4NzE1NywiYXV0aF90aW1lIjoxNjc1MTg3MTU3LCJqdGkiOiIwN2EyOWFiNy04MGY1LTQ2NmEtOGIwYi1lM2E4M2RmNDZkN2IiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgxODAvcmVhbG1zL21hc3RlciIsInN1YiI6ImU4ZWE2NWU3LWQ3NTYtNDU1Yy05YjRjLTdjZjg3NzE1MjA5OSIsInR5cCI6IkJlYXJlciIsImF6cCI6InNlY3VyaXR5LWFkbWluLWNvbnNvbGUiLCJub25jZSI6IjQ1NjczOTkzLWFjYmQtNDdhNy1iZDlhLWZmNjAwZDU2OWE4MCIsInNlc3Npb25fc3RhdGUiOiI3ZDU3ZmFlNC1jNjFiLTRlOTMtYTk4Yi1iNGNmNjgwZDNhZjQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODE4MCJdLCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwic2lkIjoiN2Q1N2ZhZTQtYzYxYi00ZTkzLWE5OGItYjRjZjY4MGQzYWY0IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbiJ9.CuNgCgkJgApNhcKKGwt-S5MVvp1K-vxjPIGS-VqqeiKSf2RMmpHVlr1znTMSZSn29rFhvSLZxM5BXTbu2CoavcnZ3R9FZ3GBDlYuYqRJYZd9wczJlQtcoD_oKBis__cGbOTXJzbkqMEXX3-bWM5vr7Lu58-I3nOfvwvvAGSMzZOcINlfr6cGdolb4ezRKMAchv7aOcccCbu0sCwwDAnDf0y1NeKJpHrEOkz4qwzL16qbjq_YYjC1sQiWfg5b4T1HXGxDCZ8lzGx_ld-fmsIu1h2q7xfXRT3KT22da1TLY0JtyifXqgmBy5mtiOHzWIPTAmIqPbJDY19I7rx3jKqW6g" 
-H "Content-Type: application/json" 
-H "Content-Length: 2" 
-H "Origin: http://localhost:8180" 
-H "Connection: keep-alive" 
-H "Sec-Fetch-Dest: empty" 
-H "Sec-Fetch-Mode: cors" 
-H "Sec-Fetch-Site: same-origin" 
-H "DNT: 1" 
-H "Pragma: no-cache" 
-H "Cache-Control: no-cache"