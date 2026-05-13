package br.com.github.renato28.agendamentoaulaapi.repository;


import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByNomeContainingIgnoreCase(String nome);

    List<Curso> findByProfessor(Usuario professor);

    List<Curso> findByDuracao(Integer duracao);

    List<Curso> findByPrecoLessThanEqual(java.math.BigDecimal preco);

    List<Curso> findByPrecoGreaterThanEqual(java.math.BigDecimal preco);
}