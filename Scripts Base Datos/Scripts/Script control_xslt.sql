DROP SCHEMA IF EXISTS control_xslt CASCADE;
CREATE SCHEMA control_xslt;

CREATE TABLE control_xslt.control_xslt (
    id_control_xslt serial NOT NULL,
    nombre varchar(50) NOT NULL,
    estructura XML NOT NULL,
    CONSTRAINT control_xslt_pk PRIMARY KEY (id_control_xslt)
);
