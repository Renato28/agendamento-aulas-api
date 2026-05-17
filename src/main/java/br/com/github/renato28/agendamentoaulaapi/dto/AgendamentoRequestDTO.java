package br.com.github.renato28.agendamentoaulaapi.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgendamentoRequestDTO {
    @NotNull(message = "O ID do aluno é  obrigatório")
    private Long alunoId;

    @NotNull(message = "O ID professor do é obrigatório")
    private Long professorId;

    @NotNull(message = "O ID do curso é obrigatório")
    private Long cursoId;

    @NotNull(message = "O ID do horário é obrigatório")
    private Long horarioId;

    @NotNull(message = "O status do agendamento é  obrigatório")
    private Long statusId;


    private String linkReuniao;


}

