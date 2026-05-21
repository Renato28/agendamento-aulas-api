package br.com.github.renato28.agendamentoaulaapi.exceptions;

public class HorarioNaoEncontradoException extends RuntimeException{
    public HorarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
