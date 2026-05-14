package br.com.github.renato28.agendamentoaulaapi.exceptions;

public class DisponibilidadeNaoEncontradoException extends RuntimeException{
    public DisponibilidadeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
