-- INTERNAL ORDER 1
INSERT INTO CLIENT_DATA(ID, VERSION, SALUTATION_ID, NAME, SURNAME)
VALUES ('CLIENT_DATA_ID_1', CURRENT_DATE, 'salutations-herr', 'TEST_NAME', 'TEST_SURNAME');

INSERT INTO PERSONAL_COMPUTER(ID, VERSION, GRAPHICS_CARD, PROCESSOR)
VALUES ('PERSONAL_COMPUTER_ID_1', CURRENT_DATE, 'GRAPHICS_CARD_1', 'PROCESSOR_1');

INSERT INTO INTERNAL_ORDER(ID, VERSION, CLIENT_DATA_ID, PERSONAL_COMPUTER_ID)
VALUES ('INTERNAL_ORDER_ID_1', CURRENT_DATE, 'CLIENT_DATA_ID_1', 'PERSONAL_COMPUTER_ID_1');

-- INTERNAL ORDER 2 (DELETED)
INSERT INTO CLIENT_DATA(ID, VERSION, DATE_OF_DELETION, SALUTATION_ID, NAME, SURNAME)
VALUES ('CLIENT_DATA_ID_2', DATEADD('DAY', -2, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE), 'salutations-herr', 'TEST_NAME', 'TEST_SURNAME');

INSERT INTO PERSONAL_COMPUTER(ID, VERSION, DATE_OF_DELETION, GRAPHICS_CARD, PROCESSOR)
VALUES ('PERSONAL_COMPUTER_ID_2', DATEADD('DAY', -2, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE), 'GRAPHICS_CARD_1', 'PROCESSOR_1');

INSERT INTO INTERNAL_ORDER(ID, VERSION, DATE_OF_DELETION, CLIENT_DATA_ID, PERSONAL_COMPUTER_ID)
VALUES ('INTERNAL_ORDER_ID_2', DATEADD('DAY', -2, CURRENT_DATE), DATEADD('DAY', -1, CURRENT_DATE), 'CLIENT_DATA_ID_2', 'PERSONAL_COMPUTER_ID_2');