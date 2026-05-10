CREATE TABLE disponibilidade
(
    id BIGSERIAL PRIMARY KEY,
    professor_id BIGINT   NOT NULL,
    dia_semana   SMALLINT NOT NULL CHECK (
    dia_semana BETWEEN 0 AND 6
        ),
    hora_inicio  TIME     NOT NULL,
    hora_fim     TIME     NOT NULL,
    duracao_slot INT      NOT NULL,

    CONSTRAINT fk_disponibilidade_professor
        FOREIGN KEY (professor_id)
            REFERENCES usuarios (id)
);