package br.com.github.renato28.agendamentoaulaapi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvaliacaoRequestDTO {

    @NotNull(message = "O aluno é obrigatório")
    private Long alunoId;

    @NotNull(message = "O Professor é obrigatório")
    private Long professorId;

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota mínima é 1")
    @Max(value = 5, message = "A nota máxima é 5")
    private Integer nota;

    private String comentario;
}
