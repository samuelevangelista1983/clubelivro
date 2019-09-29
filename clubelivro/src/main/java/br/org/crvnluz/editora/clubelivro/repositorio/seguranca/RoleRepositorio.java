package br.org.crvnluz.editora.clubelivro.repositorio.seguranca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.seguranca.Papel;

@Repository
public interface RoleRepositorio extends JpaRepository<Papel, Long> {

	public Papel findByNome(String nome);
	
}
