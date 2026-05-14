package br.com.github.renato28.agendamentoaulaapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CursoRequestDTO {

    @NotBlank(message = "O nome do curso é obrigatório")
    private String nome;

    private String descricao;


    @NotNull(message = "A duração é obrigatória")
    @Positive(message="A duração deve ser maior que zero")

    private Integer duracao;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "O professor é obrigatório")
    private Long professorId;

}
