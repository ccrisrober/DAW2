CREATE TABLE IF NOT producto (
    id int not null auto increment,
    name varchar 100 not null,
    categoria enum ("Alimentacion", "Droguería", "Prensa", "Ferretería") not null,
    precio decimal not null,
    PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXIST pedido (
);

