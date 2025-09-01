ALTER TABLE medicos RENAME COLUMN estado to uf;
ALTER TABLE medicos RENAME COLUMN rua to logradouro;

ALTER TABLE pacientes RENAME COLUMN estado to uf;
ALTER TABLE pacientes RENAME COLUMN rua to logradouro;