package br.org.crvnluz.editora.clubelivro.repositorio.integrante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;

@Repository
public interface IntegranteRepositorio extends JpaRepository<Integrante, Long> {

	public Integrante findByCpf(String cpf);
	
}
