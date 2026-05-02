CREATE TABLE cursos (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(150) NOT NULL,
                        descricao TEXT,
                        duracao INT NOT NULL,
                        preco NUMERIC(10,2),
                        professor_id BIGINT NOT NULL,
                        data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_curso_professor
                            FOREIGN KEY (professor_id)
                                REFERENCES usuarios(id)
);