package br.com.github.renato28.agendamentoaulaapi.repository;


import br.com.github.renato28.agendamentoaulaapi.model.Disponibilidade;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {

    List<Disponibilidade> findByProfessor(Usuario professor);

    List<Disponibilidade> findByDiaSemana(Integer diaSemana);

    List<Disponibilidade> findByProfessorAndDiaSemana(Usuario professor, Integer diaSemana);

    List<Disponibilidade> findByHoraInicio(LocalTime horaInicio);

    List<Disponibilidade> findByDuracaoAula(Integer duracaoAula);
}
