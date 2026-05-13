package br.com.github.renato28.agendamentoaulaapi.repository;

import br.com.github.renato28.agendamentoaulaapi.model.Perfil;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByPerfil(Perfil perfil);

    List<Usuario> findByAtivo(Boolean ativo);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    List<Usuario> findByPerfilAndAtivo(Perfil perfil, Boolean ativo);
}