package br.com.github.renato28.agendamentoaulaapi.dto;



import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisponibilidadeRequestDTO {

    @NotNull(message = "O professor é obrigatório")
    private Long professorId;

    @NotNull(message = "O dia da semana é obrigatório")
    @Min(value = 1, message = "O dia da semana deve ser entre 1 e 7")
    @Max(value = 7, message = "O dia da semana deve ser entre 1 e 7")
    private Integer diaSemana;

    @NotNull(message = "A hora inicial é obrigatória")
    private LocalTime horaInicio;

    @NotNull(message = "A hora final é obrigatória")
    private LocalTime horaFim;

    @NotNull(message = "A duração da aula é obrigatória")
    @Positive(message = "A duração da aula deve ser maior que zero")
    private Integer duracaoAula;

}