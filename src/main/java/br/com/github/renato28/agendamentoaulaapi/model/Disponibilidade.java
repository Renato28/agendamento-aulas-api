package br.com.github.renato28.agendamentoaulaapi.model;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilidades")
public class Disponibilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Usuario professor;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "duracao_slot", nullable = false)
    private Integer duracaoSlot;

    public Long getId() {
        return id;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getDuracaoSlot() {
        return duracaoSlot;
    }

    public void setDuracaoSlot(Integer duracaoSlot) {
        this.duracaoSlot = duracaoSlot;
    }
}
