package br.com.github.renato28.agendamentoaulaapi.repository;

import br.com.github.renato28.agendamentoaulaapi.model.Agendamento;
import br.com.github.renato28.agendamentoaulaapi.model.StatusAgendamento;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {


    List<Agendamento> findByAluno(Usuario aluno);

    List<Agendamento> findByProfessor(Usuario professor);

    List<Agendamento> findByStatus(StatusAgendamento status);

    List<Agendamento> findByAlunoAndStatus(Usuario aluno, StatusAgendamento status);

    List<Agendamento> findByProfessorAndStatus(Usuario professor, StatusAgendamento status);
}
