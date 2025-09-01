CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE role (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    role_name VARCHAR(50) NOT NULL
);

CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    login VARCHAR(200) NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios_role (
    usuario_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (usuario_id, role_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE pacientes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    telefone VARCHAR(11),
    cpf VARCHAR(11),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    rua VARCHAR(100),
    bairro VARCHAR(100),
    cep VARCHAR(8),
    complemento VARCHAR(50),
    numero VARCHAR(10),
    cidade VARCHAR(50),
    estado CHAR(2)
);

CREATE TABLE medicos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL,
    telefone VARCHAR(11),
    crm VARCHAR(6),
    especialidade VARCHAR(50),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    rua VARCHAR(100),
    bairro VARCHAR(100),
    cep VARCHAR(8),
    complemento VARCHAR(50),
    numero VARCHAR(10),
    cidade VARCHAR(50),
    estado CHAR(2)
);

CREATE TABLE consultas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    medico_id UUID NOT NULL,
    paciente_id UUID NOT NULL,

    data TIMESTAMP NOT NULL,

    CONSTRAINT fk_medico FOREIGN KEY (medico_id) REFERENCES medicos(id),
    CONSTRAINT fk_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);