package br.com.github.renato28.agendamentoaulaapi.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class CursoRequestDTO {

    @NotNull(message = "O titulo é obrigatório")
    private String titulo;

    private String descricao;


    @NotNull(message = "A duaração é obrigatória")
    @Positive(message="A duração deve ser maior que zero")

    private Integer duracao;

    @DecimalMin(value = "0.0", inclusive = false,
            message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @NotNull(message = "O professor é obrigatório")
    private Long professorId;

}
