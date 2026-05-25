package br.com.github.renato28.agendamentoaulaapi.repository;

import br.com.github.renato28.agendamentoaulaapi.model.Agendamento;
import br.com.github.renato28.agendamentoaulaapi.model.StatusAgendamento;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByAluno(Usuario aluno);

    List<Agendamento> findByProfessor(Usuario professor);

    List<Agendamento> findByStatus(StatusAgendamento status);

    List<Agendamento> findByAlunoAndStatus(Usuario aluno, StatusAgendamento status);

    List<Agendamento> findByProfessorAndStatus(Usuario professor, StatusAgendamento status);


    // Buscar agendamento completo por ID
    @Query("""
           SELECT a
           FROM Agendamento a
           JOIN FETCH a.aluno
           JOIN FETCH a.professor
           JOIN FETCH a.curso
           JOIN FETCH a.horario
           WHERE a.id = :id
           """)
    Optional<Agendamento> buscarCompletoPorId(Long id);


    // Listar todos os agendamentos completos
    @Query("""
           SELECT a
           FROM Agendamento a
           JOIN FETCH a.aluno
           JOIN FETCH a.professor
           JOIN FETCH a.curso
           JOIN FETCH a.horario
           """)
    List<Agendamento> listarCompleto();
}