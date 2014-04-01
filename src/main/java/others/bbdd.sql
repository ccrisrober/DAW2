CREATE TABLE producto (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name varchar(100) not null,
    image varchar(200) not null,
    category VARCHAR(15) CONSTRAINT ctg_ck CHECK (category IN ('Alimentación', 'Droguería', 'Prensa', 'Ferretería')),
    price decimal not null,
    primary key (id)
);

CREATE TABLE pedido_producto (
    id_pedido INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),     /* esto es la foreign key con pedido*/
    id INTEGER NOT NULL,        /* Unión con el producto */
    quantity INTEGER NOT NULL DEFAULT 0,        /* Nº productos*/
    primary key(id_pedido)
);

CREATE TABLE pedido_producto (
    id_pedido INTEGER NOT NULL,     /* esto es la foreign key con pedido*/
    id INTEGER NOT NULL,        /* Unión con el producto */
    quantity INTEGER NOT NULL DEFAULT 0        /* Nº productos*/
);

CREATE TABLE pedido (
    id_pedido INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_usu INTEGER NOT NULL,        /* Unión con los usuarios*/
    date date not null
    /* falta booleano de si se ha realizado ya el envío : D */
);

CREATE TABLE usuario (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username varchar(20) not null unique,
    password varchar(20) not null,
    isAdmin boolean not null default false,
    primary key(id)
);


ALTER TABLE pedido_producto ADD FOREIGN KEY(id_pedido) REFERENCES pedido(id_pedido);
ALTER TABLE pedido_producto ADD FOREIGN KEY(id) REFERENCES producto(id);
ALTER TABLE pedido ADD FOREIGN KEY(id_usu) REFERENCES usuario(id);