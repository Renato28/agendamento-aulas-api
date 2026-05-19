package br.com.github.renato28.agendamentoaulaapi.exceptions;

public class CursoNaoEncontradoException extends RuntimeException {
    public CursoNaoEncontradoException(String message) {
        super(message);
    }
}
