CREATE TYPE status_horario as ENUM (
    'DISPONIVEL',
    'OCUPADO'
);

CREATE TABLE horarios
(
    id BIGSERIAL PRIMARY KEY,
    professor_id BIGINT    NOT NULL,
    inicio TIMESTAMP NOT NULL,
    fim TIMESTAMP NOT NULL,
    status status_horario DEFAULT 'DISPONIVEL',

    CONSTRAINT fk_horario_professor
        FOREIGN KEY (professor_id)
            REFERENCES usuarios (id)
);