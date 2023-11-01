-- liquibase formatted sql

-- changeset mnk:create_payment_system
CREATE TABLE payment_systems
(
    id integer NOT NULL,
    CONSTRAINT payment_systems_pkey PRIMARY KEY (id)
);

-- changeset mnk:create_currencies
CREATE TABLE currencies
(
    id integer NOT NULL,
    code character varying(5) COLLATE pg_catalog."default" NOT NULL,
    rate numeric(40,10) NOT NULL,
    payment_system_id integer,
    CONSTRAINT currencies_pkey PRIMARY KEY (id),
    CONSTRAINT fk_ps FOREIGN KEY (payment_system_id)
        REFERENCES payment_systems (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

-- changeset mnk:create_accounts
CREATE TABLE accounts
(
    id integer NOT NULL,
    status character varying(20) COLLATE pg_catalog."default",
    account_type character varying(10) COLLATE pg_catalog."default" NOT NULL,
    payment_system_id integer,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT fk_ps FOREIGN KEY (payment_system_id)
        REFERENCES payment_systems (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

-- changeset mnk:create_fki_ps
CREATE INDEX fki_fk_ps
    ON accounts USING btree
    (payment_system_id ASC NULLS LAST);

-- changeset mnk:create_ui_acctype_ps
create unique index ui_rootacc_ps
on accounts (account_type, payment_system_id)
where account_type = 'root';

-- changeset mnk:create_payments
CREATE TABLE payments
(
    amount numeric(40,10) NOT NULL,
    status character varying(20) COLLATE pg_catalog."default",
    currency_id integer,
    sender_id integer,
    receiver_id integer,
    id character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pkey PRIMARY KEY (id),
    CONSTRAINT fk_cur FOREIGN KEY (currency_id)
        REFERENCES currencies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_rec FOREIGN KEY (receiver_id)
        REFERENCES accounts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_send FOREIGN KEY (sender_id)
        REFERENCES accounts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

-- changeset mnk:create_fki_cur
CREATE INDEX fki_fk_cur
    ON payments USING btree
    (currency_id ASC NULLS LAST);

-- changeset mnk:create_fki_rec
CREATE INDEX fki_fk_rec
    ON payments USING btree
    (receiver_id ASC NULLS LAST);

-- changeset mnk:create_fki_send
CREATE INDEX fki_fk_send
    ON payments USING btree
    (sender_id ASC NULLS LAST);