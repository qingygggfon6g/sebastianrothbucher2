-- Table: address

-- DROP TABLE address;

CREATE TABLE address
(
  id serial NOT NULL,
  anrede integer,
  email character varying(255),
  geburtsdatum date,
  nachname character varying(255),
  vorname character varying(255),
  CONSTRAINT address_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE address
  OWNER TO postgres;


-- Table: transfer

-- DROP TABLE transfer;

CREATE TABLE transfer
(
  procid text,
  attribute text,
  val text,
  topic text
)
WITH (
  OIDS=FALSE
);
ALTER TABLE transfer
  OWNER TO postgres;
