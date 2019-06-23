package crvnluz.cobcaixa.repositorio.hsql;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import crvnluz.cobcaixa.entidade.hsql.BoletoPendente;

public interface BoletoPendenteRepositorio extends JpaRepository<BoletoPendente, Long> {
	
	@Query("from BoletoPendente where momentoEnvio < ?1 and enviado = false order by momentoEnvio")
	public List<BoletoPendente> getBoletosPendentes(LocalDateTime momento, Pageable limite);
	
	@Query("from BoletoPendente where enviado = true")
	public List<BoletoPendente> getBoletosEnviados();
}
