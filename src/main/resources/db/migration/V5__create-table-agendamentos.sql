CREATE TYPE status_agendamento as ENUM (
    'AGENDADO',
    'CANCELADO',
    'CONCLUIDO'
);

CREATE TABLE agendamentos
(
    id   BIGSERIAL PRIMARY KEY,
    aluno_id BIGINT    NOT NULL,
    professor_id BIGINT    NOT NULL,
    curso_id     BIGINT    NOT NULL,
    horario_id   BIGINT    NOT NULL UNIQUE,
    status       status_agendamento DEFAULT 'AGENDADO',
    link_reuniao VARCHAR(255),
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_agendamento_aluno
        FOREIGN KEY (aluno_id)
            REFERENCES usuarios (id),

    CONSTRAINT fk_agendamento_professor
        FOREIGN KEY (professor_id)
            REFERENCES usuarios (id),

    CONSTRAINT fk_agendamento_curso
        FOREIGN KEY (curso_id)
            REFERENCES cursos (id),

    CONSTRAINT fk_agendamento_horario
        FOREIGN KEY (horario_id)
            REFERENCES horarios (id)
);