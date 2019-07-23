package br.org.crvnluz.editora.clubelivro.integracao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.crvnluz.editora.clubelivro.integracao.entidade.IntegrantePendente;

@Repository
public interface IntegrantePendenteRepositorio extends JpaRepository<IntegrantePendente, Long> {
	
}
