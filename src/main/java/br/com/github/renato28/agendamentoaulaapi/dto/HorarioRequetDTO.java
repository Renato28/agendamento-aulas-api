package br.com.github.renato28.agendamentoaulaapi.dto;

import br.com.github.renato28.agendamentoaulaapi.model.StatusHorario;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class HorarioRequetDTO {

    @NotNull(message = "O professor é obrigatório")
    private Long professorId;

    @NotBlank(message = "O status do Horario é obrigatório")

    private StatusHorario statusHorario;



}
