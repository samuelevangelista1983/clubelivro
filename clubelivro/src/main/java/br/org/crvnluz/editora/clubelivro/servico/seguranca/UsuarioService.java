package br.org.crvnluz.editora.clubelivro.servico.seguranca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.entidade.seguranca.Papel;
import br.org.crvnluz.editora.clubelivro.entidade.seguranca.Usuario;
import br.org.crvnluz.editora.clubelivro.infra.exception.ValidacaoException;
import br.org.crvnluz.editora.clubelivro.repositorio.seguranca.RoleRepositorio;
import br.org.crvnluz.editora.clubelivro.repositorio.seguranca.UsuarioRepositorio;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	@Autowired
	private RoleRepositorio roleRepositorio;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private void validarSenha(String senha) throws ValidacaoException {
		if (!senha.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{10,}$")) {
			throw new ValidacaoException("A senha informada é inválida. A senha deve ter no mínimo 10 caracteres e deve ser composta por números, letras maiúsculas e minúsculas além de caracteres especiais");
		}
	}
	
	public Usuario getUsuario(String email) {
		return usuarioRepositorio.findByEmail(email);
	}
	
	public void salvar(Usuario usuario, String senha, String confirmacaoSenha) throws ValidacaoException {
		if (StringUtil.stringNulaOuVazia(usuario.getNome())) {
			throw new ValidacaoException("O nome do usuário deve ser informado");
		}
		
		if (usuarioRepositorio.countByNome(usuario.getNome()) > 1) {
			throw new ValidacaoException("Já existe um usuário cadastrado com o nome informado");
		}
		
		if (StringUtil.stringNulaOuVazia(usuario.getEmail())) {
			throw new ValidacaoException("O e-mail do usuário deve ser informado");
		}
		
		if (usuarioRepositorio.countByEmail(usuario.getEmail()) > 1) {
			throw new ValidacaoException("Já existe um usuário cadastrado com o e-mail informado");
		}
		
		if (StringUtil.stringNaoNulaENaoVazia(senha) && StringUtil.stringNaoNulaENaoVazia(confirmacaoSenha)) {
			if (!senha.equals(confirmacaoSenha)) {
				throw new ValidacaoException("A senha informada não confere com a sua confirmação");
			}
			
			validarSenha(senha);
			usuario.setSenha(passwordEncoder.encode(senha));
			
		} else if (StringUtil.stringNulaOuVazia(senha) && StringUtil.stringNulaOuVazia(confirmacaoSenha)) {
			// Só pode deixar o campo senha em branco em casos de edição de um usuário já cadastrado
			Long id = usuario.getId();
			
			if (id == null) {
				throw new ValidacaoException("A senha do usuário e a sua confirmação devem ser informadas");
			}
			
			Usuario usuarioCadastrado = usuarioRepositorio.findById(id).get();
			usuario.setSenha(usuarioCadastrado.getSenha());
			
		} else {
			throw new ValidacaoException("A senha informada não confere com a sua confirmação");
		}
		
		usuario.setAtivo(true);
		usuario.setTrocarSenha(false);
		Papel role = roleRepositorio.findByNome("OPERADOR");
		usuario.setPapeis(new HashSet<Papel>(Arrays.asList(role)));
		usuarioRepositorio.save(usuario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByEmail(email);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		Set<Papel> papeis = usuario.getPapeis();
		List<GrantedAuthority> authorities = new ArrayList<>(papeis.size());
		papeis.stream().forEach(papel -> {
			authorities.add(new SimpleGrantedAuthority(papel.getNome()));
		});
		return new User(usuario.getEmail(), usuario.getSenha(), authorities);
	}
	
}
