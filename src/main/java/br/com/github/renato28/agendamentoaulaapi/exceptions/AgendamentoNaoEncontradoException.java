package br.com.github.renato28.agendamentoaulaapi.exceptions;

public class AgendamentoNaoEncontradoException extends RuntimeException {
    public AgendamentoNaoEncontradoException(String message) {
        super(message);
    }
}
