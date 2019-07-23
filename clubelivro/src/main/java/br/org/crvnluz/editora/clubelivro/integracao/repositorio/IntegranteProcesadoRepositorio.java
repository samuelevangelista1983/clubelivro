package br.org.crvnluz.editora.clubelivro.integracao.repositorio;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.org.crvnluz.editora.clubelivro.integracao.entidade.IntegranteProcessado;

public interface IntegranteProcesadoRepositorio extends JpaRepository<IntegranteProcessado, Long> {

	@Query("from IntegranteProcessado where tentativas < ?1 and (proximaTentativa is null or proximaTentativa < ?2) order by id")
	public List<IntegranteProcessado> getIntegranteProcessados(int tentativas, LocalDateTime tempo, Pageable limite); 
	
}
