INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Granoturco', 500, ' Il granoturco colorato è ricco di antiossidanti che combattono lo stress ossidativo e proteggono le cellule dai radicali liberi, promuovendo la salute.', 'granoturco.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Prezzemolo', 500, 'Un erba aromatica comune utilizzata in molte cucine.', 'prezzemolo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Basilico', 200, 'Foglie verdi aromatiche utilizzate soprattutto nella cucina italiana.', 'timo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Rosmarino', 100, 'Una pianta aromatica con foglie aghiformi, usata per insaporire.', 'rosmarino.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Timo', 150, 'Erba aromatica con un sapore forte e pungente.', 'timo.png');
INSERT INTO ingredient (id, name, quantita, description, image) VALUES(nextval('ingredient_seq'), 'Origano', 50, 'Erba mediterranea usata essenzialmente nella cucina italiana.', 'origano.png');

INSERT INTO users (id, name, surname, email, date_of_birth, job, image) VALUES (nextval('users_seq'), 'Gordon', 'Ramsay', 'gordon@example.com', '08-11-1966', 'Cuoco', 'Joao Ratao.png');
INSERT INTO users (id, name, surname, email, date_of_birth, job, image) VALUES (nextval('users_seq'), 'Jamie', 'Oliver', 'jamie@example.com', '27-05-1975', 'Cuoco', 'Marco Santini.png');

INSERT INTO recipe (id, name, description, preparation, image, cooke_id) VALUES (nextval('recipe_seq'), 'Cachupa', 'A fresh salad with tomatoes and basil.', 'frase ad effetto', 'Cachupa.png', 1);
INSERT INTO recipe (id, name, description, preparation, image, cooke_id) VALUES (nextval('recipe_seq'), 'Gumbo', 'Questa zuppa densa e ricca è una miscela deliziosa di gamberi freschi, salsiccia affumicata e il trinity creolo di sedano, peperoni e cipolle. Il segreto del suo sapore intenso risiede nel roux, una base di farina tostata che aggiunge profondità e consistenza al piatto', '1. Iniziate preparando il roux: cuocete lentamente la farina in una padella fino a quando non raggiunge un colore nocciola scuro. 2. Aggiungete il trinity creolo al roux e fate soffriggere fino a quando le verdure non diventano tenere. 3. Aggiungete la salsiccia affumicata e continuate a cuocere per alcuni minuti. 4. Versate il brodo di pollo, aggiungete i pomodori a pezzetti e condite con pepe di Caienna, foglie di alloro e timo fresco. Cuocete a fuoco lento per almeno un' 'ora, aggiungendo i gamberi negli ultimi 5-10 minuti di cottura per evitare che diventino gommosi. Servite il vostro Gumbo ben caldo su un letto di riso bianco, con una spruzzata di cipollotto fresco per un tocco finale autentico.', 'gumbo.png', 51);

-- Cachupa

INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (1, 1); 
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (1, 51);
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (1, 101); 

-- Gumbo

INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (51, 1); 
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (51, 51);
INSERT INTO recipe_ingredients (recipes_id, ingredients_id) VALUES (51, 101); 

-- Inserimento di esempi di post

INSERT INTO post (title, content, image, created_at) VALUES('Benvenuti ', ' Cari amici della buona cucina e delle avventure gastronomiche, benvenuti nel mio angolo virtuale dedicato alla magica cucina creola di New Orleans! Qui, viaggeremo insieme attraverso le strade profumate di spezie, gli aromi ricchi di storia e i sapori che fanno battere il cuore di questa città così unica nel suo genere. Che siate appassionati di storia, amanti del cibo o viaggiatori della cucina, questo blog è per voi. Esplorate con me la cucina creola di New Orleans e lasciatevi ispirare dai suoi sapori unici e dalla sua ricca tradizione. Accendete i fornelli, mettetevi comodi e preparatevi per un''avventura culinaria che vi lascerà con il desiderio di tornare per gustare ancora. Benvenuti nella mia cucina creola. Buon appetito!', 'benvenuti.png ', CURRENT_TIMESTAMP);
INSERT INTO post (title, content, image, created_at) VALUES('Storia della cucina Creola', 'Oggi ci immergeremo nella vibrante cultura gastronomica di New Orleans, famosa per il suo melting pot di sapori e per le sue vivaci celebrazioni. La cucina creola di New Orleans ha radici profonde che risalgono al periodo coloniale. Nata dalla mescolanza delle culture africana, francese, spagnola e delle popolazioni native americane, la cucina creola si è sviluppata nelle case e nei mercati della città, riflettendo l''ingegnosità e la creatività delle persone che vi abitavano. Durante il periodo coloniale francese e spagnolo, molte famiglie aristocratiche e i proprietari delle piantagioni in Louisiana impiegavano cuochi africani e creoli per preparare pasti che combinavano ingredienti locali con tecniche di cottura sofisticate. Questo ha portato alla creazione di piatti iconici come il Gumbo, il Jambalaya e il Red Beans and Rice, che sono diventati simboli della cultura gastronomica creola.', '1post.png', CURRENT_TIMESTAMP);
