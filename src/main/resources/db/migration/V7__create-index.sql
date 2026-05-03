CREATE INDEX idx_horarios_professor_inicio
    ON horarios (professor_id, inicio);

CREATE INDEX idx_agendamentos_aluno
    ON agendamentos (aluno_id);

CREATE INDEX idx_agendamentos_professor
    ON agendamentos (professor_id);