package crvnluz.cobcaixa.repositorio.hsql;

import org.springframework.data.jpa.repository.JpaRepository;

import crvnluz.cobcaixa.entidade.hsql.DiaProcessado;

public interface DiaProcessadoRepositorio extends JpaRepository<DiaProcessado, Long> {
	
	public DiaProcessado findByStatus(Integer status);
	
}
