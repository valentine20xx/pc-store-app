-- Order statuses
INSERT INTO GLOBAL_VARIABLE(ID, VERSION, TYPE, SUBTYPE, NAME, DESCRIPTION, DELETABLE)
VALUES ('order-status-open', CURRENT_DATE, 'order-status', 'open', 'Open', 'Order is open', false);

INSERT INTO GLOBAL_VARIABLE(ID, VERSION, TYPE, SUBTYPE, NAME, DESCRIPTION, DELETABLE)
VALUES ('order-status-producing', CURRENT_DATE, 'order-status', 'producing', 'Producing', 'Producing', false);

INSERT INTO GLOBAL_VARIABLE(ID, VERSION, TYPE, SUBTYPE, NAME, DESCRIPTION, DELETABLE)
VALUES ('order-status-sent', CURRENT_DATE, 'order-status', 'sent', 'Sent', 'Sent', false);

-- Salutations
INSERT INTO GLOBAL_VARIABLE(ID, VERSION, TYPE, SUBTYPE, NAME, DESCRIPTION, DELETABLE)
VALUES ('salutations-herr', CURRENT_DATE, 'salutations', 'herr', 'Herr', 'eine Person mit Penis', false);

INSERT INTO GLOBAL_VARIABLE(ID, VERSION, TYPE, SUBTYPE, NAME, DESCRIPTION, DELETABLE)
VALUES ('salutations-frau', CURRENT_DATE, 'salutations', 'frau', 'Frau', 'eine Person mit Vagina', false);

INSERT INTO GLOBAL_VARIABLE(ID, VERSION, TYPE, SUBTYPE, NAME, DESCRIPTION, DELETABLE)
VALUES ('salutations-divers', CURRENT_DATE, 'salutations', 'divers', 'Divers', 'Etwas anderes (z.B. Außerirdischer, Alien, Nicht-menstruierende Person usw)', false);