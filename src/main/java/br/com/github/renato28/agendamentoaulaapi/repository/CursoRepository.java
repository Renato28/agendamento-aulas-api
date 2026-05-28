package br.com.github.renato28.agendamentoaulaapi.repository;

import br.com.github.renato28.agendamentoaulaapi.model.Curso;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("""
           SELECT c
           FROM Curso c
           JOIN FETCH c.professor
           WHERE c.id = :id
           """)
    Optional<Curso> buscarCompletoPorId(Long id);


    @Query("""
           SELECT c
           FROM Curso c
           JOIN FETCH c.professor
           """)
    List<Curso> listarCompleto();


    @Query("""
           SELECT c
           FROM Curso c
           WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
           """)
    List<Curso> findByNomeContainingIgnoreCase(String nome);


    @Query("""
           SELECT c
           FROM Curso c
           WHERE c.professor = :professor
           """)
    List<Curso> findByProfessor(Usuario professor);


    @Query("""
           SELECT c
           FROM Curso c
           WHERE c.duracao = :duracao
           """)
    List<Curso> findByDuracao(Integer duracao);


    @Query("""
           SELECT c
           FROM Curso c
           WHERE c.preco <= :preco
           """)
    List<Curso> findByPrecoLessThanEqual(BigDecimal preco);


    @Query("""
           SELECT c
           FROM Curso c
           WHERE c.preco >= :preco
           """)
    List<Curso> findByPrecoGreaterThanEqual(BigDecimal preco);
}