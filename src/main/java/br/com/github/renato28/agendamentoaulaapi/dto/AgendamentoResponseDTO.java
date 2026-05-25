package br.com.github.renato28.agendamentoaulaapi.dto;

import br.com.github.renato28.agendamentoaulaapi.model.Agendamento;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AgendamentoResponseDTO {

    private String nomeAluno;

    private String nomeProfessor;

    private String nomeCurso;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private String linkReuniao;

    public static AgendamentoResponseDTO fromEntity(
            Agendamento agendamento
    ) {

        return AgendamentoResponseDTO.builder()

                .nomeAluno(
                        agendamento.getAluno().getNome()
                )

                .nomeProfessor(
                        agendamento.getProfessor().getNome()
                )

                .nomeCurso(
                        agendamento.getCurso().getNome()
                )

                .dataInicio(
                        agendamento.getHorario().getInicio()
                )

                .dataFim(
                        agendamento.getHorario().getFim()
                )

                .linkReuniao(
                        agendamento.getLinkReuniao()
                )

                .build();
    }
}