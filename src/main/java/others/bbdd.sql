CREATE TABLE Producto (
    id_prod INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name varchar(100) not null,
    image varchar(200) not null,
    category VARCHAR(15) CONSTRAINT ctg_ck CHECK (category IN ('Alimentación', 'Droguería', 'Prensa', 'Ferretería')),
    price decimal not null
);

ALTER TABLE Producto ADD CONSTRAINT PK_Producto PRIMARY KEY (id_prod);

CREATE TABLE Usuario (
    id_user INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username varchar(20) not null unique,
    password varchar(20) not null,
    isAdmin boolean not null default false
);

ALTER TABLE Usuario ADD CONSTRAINT PK_Usuario PRIMARY KEY (id_user);

CREATE TABLE Pedido (
    id_pedido INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    id_user integer NOT NULL,
    date date not null,
    price real not null default 0,
    procesado boolean not null default false
);

ALTER TABLE Pedido ADD CONSTRAINT PK_Pedido PRIMARY KEY (id_pedido,id_user);

CREATE TABLE Pedido_Producto (
    id_prod integer NOT NULL,
    id_pedido integer NOT NULL,
    id_user integer NOT NULL,
    quantity integer
);

ALTER TABLE Pedido_Producto ADD CONSTRAINT PK_Pedido_Producto PRIMARY KEY (id_prod,id_pedido,id_user);

ALTER TABLE Pedido ADD CONSTRAINT FK_Pedido_0 FOREIGN KEY (id_user) REFERENCES Usuario (id_user);

/*ALTER TABLE Pedido_Producto ADD CONSTRAINT FK_Pedido_Producto_0 FOREIGN KEY (id_prod) REFERENCES Producto (id_prod);*/
ALTER TABLE Pedido_Producto ADD CONSTRAINT FK_Pedido_Producto_1 FOREIGN KEY (id_pedido,id_user) REFERENCES Pedido (id_pedido,id_user);


