ALTER TABLE bioterio.solicitudes_conejera
DROP COLUMN estado;

ALTER TABLE bioterio.solicitudes_ratonera
DROP COLUMN estado;

ALTER TABLE bioterio.solicitudes_conejera
ADD COLUMN estado character varying(30);

ALTER TABLE bioterio.solicitudes_ratonera
ADD COLUMN estado character varying(30);