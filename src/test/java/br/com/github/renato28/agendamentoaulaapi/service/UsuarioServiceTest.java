package br.com.github.renato28.agendamentoaulaapi.service;

import br.com.github.renato28.agendamentoaulaapi.dto.UsuarioRequestDto;
import br.com.github.renato28.agendamentoaulaapi.model.Perfil;
import br.com.github.renato28.agendamentoaulaapi.model.Usuario;
import br.com.github.renato28.agendamentoaulaapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveCadastrarUsuarioComSucesso() {

        UsuarioRequestDto dto = new UsuarioRequestDto();
        dto.setNome("Renato Nóbrega");
        dto.setEmail("renato@teste.com");
        dto.setSenha("123456");
        dto.setPerfil(Perfil.ALUNO);

        when(usuarioRepository.existsByEmail(dto.getEmail())).thenReturn(false);

        when(passwordEncoder.encode(dto.getSenha())).thenReturn("senhaCriptografada");

        Usuario usuarioSalvo = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha("senhaCriptografada")
                .perfil(Perfil.ALUNO)
                .ativo(true)
                .build();

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        Usuario usuario = usuarioService.cadastrar(dto);

        assertNotNull(usuario);
        assertEquals("Renato Nóbrega", usuario.getNome());
        assertEquals("renato@teste.com", usuario.getEmail());

        verify(usuarioRepository, times(1))
        .save(any(Usuario.class));

    }
}
