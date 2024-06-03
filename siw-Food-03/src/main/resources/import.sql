INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Granoturco', 500, ' Il granoturco colorato è ricco di antiossidanti che combattono lo stress ossidativo e proteggono le cellule dai radicali liberi, promuovendo la salute.', 'granoturco.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Prezzemolo', 500, 'Un erba aromatica comune utilizzata in molte cucine.', 'prezzemolo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Basilico', 200, 'Foglie verdi aromatiche utilizzate soprattutto nella cucina italiana.', 'timo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Rosmarino', 100, 'Una pianta aromatica con foglie aghiformi, usata per insaporire.', 'rosmarino.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Timo', 150, 'Erba aromatica con un sapore forte e pungente.', 'timo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Origano', 50, 'Erba mediterranea usata essenzialmente nella cucina italiana.', 'origano.png');

INSERT INTO users (id, name, surname, email, date_of_birth, job, image) VALUES (nextval('users_seq'), 'Gordon', 'Ramsay', 'gordon@example.com', '08-11-1966', 'Cuoco', 'Joao Ratao.png');
INSERT INTO users (id, name, surname, email, date_of_birth, job, image) VALUES (nextval('users_seq'), 'Jamie', 'Oliver', 'jamie@example.com', '27-05-1975', 'Cuoco', 'Marco Santini.png');

INSERT INTO recipe (id, name, description, image, cooke_id) VALUES (nextval('recipe_seq'), 'Cachupa', 'A fresh salad with tomatoes and basil.', 'Cachupa.png', 1);
INSERT INTO recipe (id, name, description, image, cooke_id) VALUES (nextval('recipe_seq'), 'Gumbo', 'Classic Italian pasta with tomato sauce.', 'gumbo.png', 51);

-- Cachupa

INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (1, 1); 
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (1, 51);
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (1, 101); 

-- Gumbo

INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (51, 1); 
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (51, 51);
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (51, 101); 