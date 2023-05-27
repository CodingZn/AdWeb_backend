
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
                                    id INT NOT NULL AUTO_INCREMENT,
                                    username varchar(50) NOT NULL UNIQUE ,
                                    nickname VARCHAR(50) NOT NULL,
                                    phone VARCHAR(50) NOT NULL,
                                    email VARCHAR(100) DEFAULT NULL,
                                    salt varchar(255) not null,
                                    passwd varchar(255) not null,
                                    profileid int default 0 not null,
                                    PRIMARY KEY (id)
);
