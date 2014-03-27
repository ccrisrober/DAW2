CREATE TABLE producto (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name varchar(100) not null,
    category VARCHAR(15) CONSTRAINT ctg_ck CHECK (category IN ('Alimentación', 'Droguería', 'Prensa', 'Ferretería')),
    price decimal not null,
    primary key (id)
);
