CREATE SCHEMA clients;

CREATE SEQUENCE clients.client_id_seq;

CREATE TABLE clients.client (
    id bigint NOT NULL DEFAULT nextval('clients.client_id_seq'),
    name varchar(255) NOT NULL,
    debit_value decimal(10, 2),
    PRIMARY KEY (id)
);
