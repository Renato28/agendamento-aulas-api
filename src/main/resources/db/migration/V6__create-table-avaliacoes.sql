CREATE TABLE avaliacoes (
    id BIGSERIAL PRIMARY KEY,
    aluno_id BIGINT NOT NULL,
    professor_id BIGINT NOT NULL,
    nota INT NOT NULL CHECK (
    nota BETWEEN 1 AND 5
    ),
    comentario TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_avaliacao_aluno
    FOREIGN KEY (aluno_id)
        REFERENCES usuarios(id),
            CONSTRAINT fk_avaliacao_professor
            FOREIGN KEY (professor_id)
            REFERENCES usuarios(id)
);