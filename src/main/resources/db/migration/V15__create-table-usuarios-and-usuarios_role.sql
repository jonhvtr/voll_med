create table roles (
    id bigint auto_increment not null,
    nome varchar(50) not null unique,

    primary key(id)
);

create table usuarios_roles (
    usuario_id bigint not null,
    role_id bigint not null,

    primary key(usuario_id, role_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO roles (id, nome) VALUES (1, 'ADMIN'), (2, 'EMPLOYEE');