package br.com.github.renato28.agendamentoaulaapi.dto;


import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CursoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Integer duracao;
    private BigDecimal preco;
    private String nomeProfessor;

    public static CursoResponseDTO fromEntity(Curso curso) {

        return CursoResponseDTO.builder()
                .id(curso.getId())
                .nome(curso.getNome())
                .descricao(curso.getDescricao())
                .duracao(curso.getDuracao())
                .preco(curso.getPreco())
                .nomeProfessor(curso.getProfessor().getNome())

                .build();
    }
}
