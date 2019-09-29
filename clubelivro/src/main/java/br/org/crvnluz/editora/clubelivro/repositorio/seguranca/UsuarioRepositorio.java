package br.org.crvnluz.editora.clubelivro.repositorio.seguranca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.seguranca.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	
	public long countByEmail(String email);
	
	public long countByNome(String nome);
	
	public Usuario findByEmail(String email);
	
}
