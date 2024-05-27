INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Granoturco', 500, ' Il granoturco colorato è ricco di antiossidanti che combattono lo stress ossidativo e proteggono le cellule dai radicali liberi, promuovendo la salute.', 'granoturco.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Prezzemolo', 500, 'Un erba aromatica comune utilizzata in molte cucine.', 'prezzemolo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Basilico', 200, 'Foglie verdi aromatiche utilizzate soprattutto nella cucina italiana.', 'timo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Rosmarino', 100, 'Una pianta aromatica con foglie aghiformi, usata per insaporire.', 'rosmarino.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Timo', 150, 'Erba aromatica con un sapore forte e pungente.', 'timo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Origano', 50, 'Erba mediterranea usata essenzialmente nella cucina italiana.', 'origano.png');

INSERT INTO users (id, name, surname, email, date_of_birth) VALUES (1, 'Gordon', 'Ramsay', 'gordon@example.com', '1966-11-08');
INSERT INTO users (id, name, surname, email, date_of_birth) VALUES (2, 'Jamie', 'Oliver', 'jamie@example.com', '1975-05-27');

INSERT INTO recipe (id, name, description, image, cooke_id) VALUES (nextval('recipe_seq'), 'Cachupa', 'A fresh salad with tomatoes and basil.', 'Cachupa.png', 1);
INSERT INTO recipe (id, name, description, image, cooke_id) VALUES (nextval('recipe_seq'), 'Gumbo', 'Classic Italian pasta with tomato sauce.', 'gumbo.png', 2);


