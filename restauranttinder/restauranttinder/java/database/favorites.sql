CREATE TABLE favorites
(
        favorite_api_id int not null,
        favorite_name varChar(64) not null,
        cuisine varChar(64),
        phone_number varChar(128),
        ratings varChar(10),
        description varChar(256),
        featured_image varChar(256),
        currency varChar(10),
        url varChar(512),
        menu_url varChar(521),
        
        constraint pk_favorites primary key (favorite_api_id)
 );
 
 CREATE TABLE user_favorites
 (
        user_id int,
        favorite_api_id int,
        
        constraint pk_user_favorites primary key (user_id, favorite_api_id),
        constraint fk_user_favorites_users foreign key (user_id) references users (user_id),
        constraint fk_user_favorites_favorites foreign key (favorite_api_id) references favorites (favorite_api_id)
 );             
 
