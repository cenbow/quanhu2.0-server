-- pet store
CREATE TABLE category(
    id INT IDENTITY,
    name VARCHAR(25) NOT NULL,
    description VARCHAR(255) NOT NULL,
    imageurl VARCHAR(55),
    PRIMARY KEY (id)
);

CREATE TABLE product (
 id INT IDENTITY,
 category_id INT NOT NULL,
 name VARCHAR(25) NOT NULL,
 description VARCHAR(255) NOT NULL,
 imageurl VARCHAR(55),
 PRIMARY KEY (id),
 FOREIGN KEY (category_id) REFERENCES category(id)
);