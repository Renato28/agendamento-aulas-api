package br.com.github.renato28.agendamentoaulaapi.repository;

import br.com.github.renato28.agendamentoaulaapi.model.Horario;
import br.com.github.renato28.agendamentoaulaapi.model.StatusHorario;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    List<Horario> findByProfessor(Usuario professor);

    List<Horario> findByStatusHorario(StatusHorario statusHorario);

    List<Horario> findByProfessorAndStatusHorario(Usuario professor, StatusHorario statusHorario);

    List<Horario> findByFim(LocalDateTime fim);

    List<Horario> findByFimAfter(LocalDateTime fim);

    List<Horario> findByFimBefore(LocalDateTime fim);
}
