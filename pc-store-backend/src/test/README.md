## Testing

### Process



### addCandidateMultipart

`curl -X POST "http://localhost:8080/internal-order-multipart" -H "Content-Type: multipart/form-data" -F "internal-order={ \"clientData\": { \"salutation\": \"male\", \"name\": \"John\", \"surname\": \"Smith\", \"street\": \"Hauptstraße\", \"houseNumber\": \"110\", \"zip\": 90459, \"city\": \"Nürnberg\", \"telephone\": \"+49528252826\", \"cellphone\": \"+49528252826\", \"email\": \"example@test.de\" }, \"personalComputer\": { \"computerCase\": \"MSI MAG Forge 100R\", \"motherboard\": \"Gigabyte B550 Aorus Pro V2\", \"processor\": \"AMD Ryzen 7 5800X\", \"graphicsCard\": \"AMD Radeon RX 6700 XT 12 GB\", \"randomAccessMemory\": \"32GB Corsair Vengeance LPX DDR4-3000\", \"storageDevice\": \"250GB Samsung 870 EVO\", \"powerSupplyUnit\": \"700W - be quiet! Pure power 11\" }, \"privacyPolicy\": true, \"files\":[{ \"id\":\"1\",\"name\":\"pom.xml\" }] }" -F "1=@pom.xml"`

### download

`curl "http://localhost:8080/download/internal-order-file/3fa49708-d0c6-4612-8f09-600d4412ee9c"`