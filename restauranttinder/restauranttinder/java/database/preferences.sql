CREATE TABLE preferences
(
        cuisine_id int,
        cuisine_name varChar(64),
        
        constraint pk_preferences primary key (cuisine_id)
);

INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (193, 'BBQ');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (25, 'Chinese');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (153, 'Cuban');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (148, 'Indian');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (55, 'Italian');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (73, 'Mexican');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (82, 'Pizza');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (308, 'Vegetarian');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (162, 'Peruvian');
INSERT INTO preferences (cuisine_id, cuisine_name) VALUES (95, 'Thai');
