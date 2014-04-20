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


/* Inserciones en Producto */
INSERT INTO PRODUCTO ("NAME", IMAGE, CATEGORY, PRICE) 
	VALUES ('Cámara', 'http://www.fkcv.net/wp-content/uploads/foto4.jpg', 'Ferretería', 12.5),
    ('El País', 'http://www.vodafone.es/static/imagen/pro_ucm_mgmt_550145.png', 'Prensa', 1.0),
    ('Vanish Kalia', 'http://tienda.sancrispin.net/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/e/0/e01598.jpg', 'Droguería', 2.65),
    ('Mandarinas', 'http://naranjasking.com/481-large_default/caja-mixta-de-15-kg-de-naranjas-y-mandarinas.jpg', 'Alimentación', 2.2), 
    ('Clinit Bang', 'http://tienda.sancrispin.net/media/catalog/product/cache/1/image/9df78eab33525d08d6e5fb8d27136e95/a/7/a70895.jpg', 'Droguería', 4.5), 
    ('El Jueves', 'http://alrevesyalderecho.infolibre.es/wp-content/uploads/2014/02/eljueves.jpg', 'Prensa', 3.5),
    ('Antical', 'http://www.rdiranzo.com/admin/images/get/12059?xsfv=1291864536&&name=12059.jpg', 'Droguería', 2.75), 
    ('Tornillos', 'http://www.clubtamaran.com/sistem3.jpg', 'Ferretería', 1.75),
    ('Fresas', 'http://i0.wp.com/www.miguelpena.com/fotos/fresas.jpg', 'Alimentación', 2.25),
    ('Destornillador', 'http://www.mytrendyphone.es/images/54510_iPhone_4_4S_Baku_Precision_Screwdriver_01_04042012.jpg', 'Ferretería', 1.95),
    ('Peras', 'http://www.fruteriamadrid.com/productos/peras-agua.jpg', 'Alimentación', 1.4),
    ('Barniz Titán', 'http://www.celsomiguez.com/68078-13424-large/barniz-tinte-2010-brillante-roble.jpg', 'Droguería', 1.2), 
    ('Martillo', 'http://ferreteriamadrid.cl/wp-content/uploads/2013/07/Stanley-1007848-350x350.jpg', 'Ferretería', 2.5);

/* Inserciones en Usuario*/
INSERT INTO USUARIO (USERNAME, PASSWORD, ISADMIN) 
	VALUES ('admin', 'admin', true), ('pedro', 'pedro', false);

/* Inserciones en Pedido */
INSERT INTO PEDIDO (ID_USER, "DATE", PRICE, PROCESADO) 
	VALUES (3, '2014-04-16', 32.62, true), (3, '2014-04-16', 231.0, true);

/* Inserciones en Pedido_Producto*/
INSERT INTO PEDIDO_PRODUCTO (ID_PROD, ID_PEDIDO, ID_USER, QUANTITY) 
	VALUES (21, 2, 3, 3), (30, 2, 3, 4), (21, 3, 3, 46), (23, 3, 3, 10), 
                (22, 3, 3, 8), (29, 3, 3, 6), (28, 3, 3, 5);
