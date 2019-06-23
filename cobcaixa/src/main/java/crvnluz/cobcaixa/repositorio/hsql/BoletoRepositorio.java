package crvnluz.cobcaixa.repositorio.hsql;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import crvnluz.cobcaixa.entidade.hsql.Boleto;

public interface BoletoRepositorio extends JpaRepository<Boleto, Long> {
	
	@Query("select max(emissao) from Boleto where situacao = ?1")
	public LocalDate getDataUltimoBoleto(Integer situacao);
	
}
