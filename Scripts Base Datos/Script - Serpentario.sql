DROP SCHEMA IF EXISTS serpentario CASCADE;
CREATE SCHEMA serpentario;

CREATE TABLE serpentario.especies (
    id_especie serial NOT NULL,
    genero character varying(45) NOT NULL,
    especie character varying(45) NOT NULL
);

ALTER TABLE ONLY serpentario.especies ADD CONSTRAINT pk_especies PRIMARY KEY (id_especie);

