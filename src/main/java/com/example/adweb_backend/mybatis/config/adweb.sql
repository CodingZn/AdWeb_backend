
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS auth;
CREATE TABLE IF NOT EXISTS auth(
                                   authid INT(11) not null AUTO_INCREMENT,
                                   salt varchar(255) not null,
                                   passwd varchar(255) not null,
                                   primary key (authid)
);
CREATE TABLE IF NOT EXISTS user (
                                    id INT(11) NOT NULL AUTO_INCREMENT,
                                    username varchar(50) NOT NULL UNIQUE ,
                                    nickname VARCHAR(50) NOT NULL,
                                    phone VARCHAR(50) NOT NULL,
                                    email VARCHAR(100) DEFAULT NULL,
                                    authid int(11),
                                    PRIMARY KEY (id),
                                    foreign key (authid) references auth(authid)
);
